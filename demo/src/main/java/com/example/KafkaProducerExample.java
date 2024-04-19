package com.example;
import java.util.Properties;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import com.fasterxml.jackson.databind.ObjectMapper;
public class KafkaProducerExample {
    public static void main(String[] args) {
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        Producer<String, String> producer = new KafkaProducer<>(props);
        ObjectMapper objectMapper = new ObjectMapper();
        try {
            Student student = new Student(1, "Alice");
            String jsonStudent = objectMapper.writeValueAsString(student);
            producer.send(new ProducerRecord<>("student-topic", Integer.toString(student.getId()), jsonStudent),
                (recordMetadata, e) -> {
                    if (e != null) {
                        e.printStackTrace();
                    } else {
                        System.out.println("The offset of the record we just sent is: " + recordMetadata.offset());
                    }
                });
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            producer.close();
        }
    }
}