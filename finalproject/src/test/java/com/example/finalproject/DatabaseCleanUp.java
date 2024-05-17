//package com.example.finalproject;
//
//import jakarta.persistence.Entity;
//import jakarta.persistence.EntityManager;
//import org.springframework.beans.factory.InitializingBean;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.stream.Collectors;
//
//@Service
//public class DatabaseCleanUp implements InitializingBean {
//
//    private final EntityManager entityManager;
//    private List<String> tableNames = new ArrayList<>();
//
//    public DatabaseCleanUp(EntityManager entityManager) {
//        this.entityManager = entityManager;
//    }
//
//    @Override
//    public void afterPropertiesSet() throws Exception {
//        tableNames = entityManager.getMetamodel().getEntities().stream()
//                .filter(entityType -> entityType.getJavaType().getAnnotation(Entity.class) != null)
//                .map(entityType -> toLowerCaseUnderscore(entityType.getName()))
//                .collect(Collectors.toList());
//    }
//
//    @Transactional
//    public void truncateAllEntity() {
//        entityManager.flush();
//
//        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = FALSE").executeUpdate(); // 수정된 부분
//        for (String tableName : tableNames) {
//            entityManager.createNativeQuery("TRUNCATE TABLE " + tableName).executeUpdate();
//        }
//        entityManager.createNativeQuery("SET FOREIGN_KEY_CHECKS = TRUE").executeUpdate(); // 수정된 부분
//    }
//
//    private String toLowerCaseUnderscore(String input) {
//        // Implement your logic to convert input to lower case with underscores
//        // For example:
//        return input.replaceAll("(.)(\\p{Upper})", "$1_$2").toLowerCase();
//    }
//}
