package com.sau;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

public class Main {
    public static Properties properties = new Properties();

    public static void main(String[] args) {
        //System.out.println("Hello, World!");

        System.out.printf("Başladı");

        String bootstrapServers = "127.0.0.1:9092";

        // create Producer properties

        properties.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServers);
        properties.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        properties.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());

        // And From your main() method or any other method
        Timer timer = new Timer();
        timer.schedule(new SayHello(), 0, 1000);

        System.out.printf("Tamamdır");
    }

    static class SayHello extends TimerTask {
        public void run() {
            // create the producer
            KafkaProducer<String, String> producer = new KafkaProducer<>(properties);
            Random r = new Random();
            // create a producer record
            ProducerRecord<String, String> producerRecord =
                    new ProducerRecord<>("SAU", "Hello from Kafka producer: " + r.nextInt(10));

            // send data - asynchronous
            producer.send(producerRecord);

            // flush data - synchronous
            producer.flush();
            // flush and close producer
            producer.close();
        }
    }
}