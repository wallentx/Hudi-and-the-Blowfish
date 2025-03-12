package com.wallentx.hudi.blowfish;

import org.apache.hudi.common.model.DefaultHoodieRecordPayload;
import org.apache.hudi.common.model.HoodieRecordMetadata;
import org.apache.hudi.common.util.Option;
import org.apache.avro.generic.GenericRecord;

public class OnlyWannaBeWithYouPayload extends DefaultHoodieRecordPayload {
    private static final String SECRET_LOVE_KEY = "mySecretKey"; // Replace with secure key management
    private static final OnlyForYouEncryptor encryptor = new OnlyForYouEncryptor(SECRET_LOVE_KEY);

    public OnlyWannaBeWithYouPayload(GenericRecord record, Comparable orderingVal) {
        super(record, orderingVal);
    }

    @Override
    public Option<GenericRecord> getInsertValue(HoodieRecordMetadata recordMetadata) {
        Option<GenericRecord> original = super.getInsertValue(recordMetadata);
        if (original.isPresent()) {
            GenericRecord record = original.get();
            Object sensitiveValue = record.get("sensitiveField");
            if (sensitiveValue instanceof CharSequence) {
                try {
                    String encryptedValue = encryptor.encryptForYou(sensitiveValue.toString());
                    record.put("sensitiveField", encryptedValue);
                } catch (Exception e) {
                    throw new RuntimeException("Error encrypting sensitiveField", e);
                }
            }
        }
        return original;
    }
}
