package com.kafka.demo.controller;

import org.apache.kafka.clients.admin.AdminClient;
import org.apache.kafka.clients.admin.DescribeClusterResult;
import org.apache.kafka.clients.admin.ListTopicsResult;
import org.apache.kafka.common.Node;
import org.springframework.kafka.core.KafkaAdmin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ExecutionException;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/cluster")
public class ClusterController {

    private final KafkaAdmin kafkaAdmin;

    public ClusterController(KafkaAdmin kafkaAdmin) {
        this.kafkaAdmin = kafkaAdmin;
    }

    @GetMapping("/health")
    public Map<String, Object> getHealth() {
        Map<String, Object> status = new HashMap<>();
        try (AdminClient client = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            DescribeClusterResult cluster = client.describeCluster();
            Collection<Node> nodes = cluster.nodes().get();
            status.put("status", "UP");
            status.put("nodeCount", nodes.size());
            status.put("nodes", nodes.stream().map(Node::toString).collect(Collectors.toList()));
        } catch (Exception e) {
            status.put("status", "DOWN");
            status.put("error", e.getMessage());
        }
        return status;
    }

    @GetMapping("/info")
    public Map<String, Object> getInfo() {
        Map<String, Object> info = new HashMap<>();
        try (AdminClient client = AdminClient.create(kafkaAdmin.getConfigurationProperties())) {
            DescribeClusterResult cluster = client.describeCluster();
            info.put("clusterId", cluster.clusterId().get());
            info.put("controller", cluster.controller().get().toString());
            
            ListTopicsResult topicsResult = client.listTopics();
            Set<String> names = topicsResult.names().get();
            info.put("topics", names);
            info.put("topicCount", names.size());
        } catch (Exception e) {
            info.put("error", e.getMessage());
        }
        return info;
    }
}
