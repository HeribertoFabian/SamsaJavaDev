package com.fash.example.kafkav.secondexample.customserializers.partitioner;

import java.util.List;
import java.util.Map;

import org.apache.kafka.clients.producer.Partitioner;
import org.apache.kafka.common.Cluster;
import org.apache.kafka.common.PartitionInfo;

import io.confluent.common.utils.Utils;

public class VIPPartitioner implements Partitioner {

	@Override
	public void configure(Map<String, ?> configs) {

	}

	@Override
	public int partition(String topic, Object key, byte[] keyBytes, Object value, byte[] valueBytes, Cluster cluster) {
		List<PartitionInfo> partitions = cluster.availablePartitionsForTopic(topic);
		if(((String)key).equals("Heriberto5")) {
		return 5;
		}
		return Math.abs(Utils.murmur2(keyBytes)%partitions.size()-1);
	}

	@Override
	public void close() {

	}

}
