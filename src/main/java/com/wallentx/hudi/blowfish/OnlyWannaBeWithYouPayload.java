package com.wallentx.hudi.blowfish;

import org.apache.avro.Schema;
import org.apache.avro.generic.GenericRecord;
import org.apache.avro.generic.IndexedRecord;
import org.apache.hudi.common.model.DefaultHoodieRecordPayload;
import org.apache.hudi.common.util.Option;
import java.io.IOException;
import java.security.GeneralSecurityException;

public class OnlyWannaBeWithYouPayload extends DefaultHoodieRecordPayload {

    private static final String SECRET_KEY = "mySecretKey"; // Replace with secure key management
    private static final OnlyForYouEncryptor encryptor = new OnlyForYouEncryptor(SECRET_KEY);

    public OnlyWannaBeWithYouPayload(GenericRecord record, Comparable orderingVal) {
        super(record, orderingVal);
    }

    @Override
    public Option<IndexedRecord> getInsertValue(Schema schema) {
        Option<IndexedRecord> originalOption;
        try {
            originalOption = super.getInsertValue(schema);
        } catch (IOException e) {
            throw new RuntimeException("IO error while retrieving insert value", e);
        }

        if (originalOption.isPresent()) {
            IndexedRecord record = originalOption.get();
            if (record instanceof GenericRecord) {
                GenericRecord genRecord = (GenericRecord) record;
                Object sensitiveValue = genRecord.get("sensitiveField");

                if (sensitiveValue instanceof CharSequence) {
                    try {
                        String encryptedValue = encryptor.encryptForYou(sensitiveValue.toString());
                        genRecord.put("sensitiveField", encryptedValue);
                    } catch (GeneralSecurityException e) {
                        throw new RuntimeException("Encryption error during processing", e);
                    }
                }
                return Option.of(genRecord);
            }
        }
        return Option.empty();
    }
}
