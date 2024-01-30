package com.dl.officialsite.common.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;

public class MerkleTree {
    private List<String> transactions;
    private String root;

    public MerkleTree(List<String> transactions) {
        this.transactions = transactions;
        this.root = "";
    }

    public String buildTreeRoot() {
        List<String> tempTransactions = new ArrayList<>(transactions);

        while (tempTransactions.size() > 1) {
            List<String> newTransactions = new ArrayList<>();

            for (int i = 0; i < tempTransactions.size(); i += 2) {
                String left = tempTransactions.get(i);
                String right = (i + 1 < tempTransactions.size()) ? tempTransactions.get(i + 1) : left;
                String combined = left + right;
                String hash = applySHA256(combined);
                newTransactions.add(hash);
            }

            tempTransactions = newTransactions;
        }

        this.root = (tempTransactions.size() == 1) ? tempTransactions.get(0) : "";
        return this.root;
    }

    public String generateProof(long index) {
        List<String> tempTransactions = new ArrayList<>(transactions);
        List<String> proof = new ArrayList<>();

        while (tempTransactions.size() > 1) {
            List<String> newTransactions = new ArrayList<>();

            for (int i = 0; i < tempTransactions.size(); i += 2) {
                String left = tempTransactions.get(i);
                String right = (i + 1 < tempTransactions.size()) ? tempTransactions.get(i + 1) : left;
                String combined = left + right;
                String hash = applySHA256(combined);
                newTransactions.add(hash);

                if (i == index || i + 1 == index) {
                    proof.add(hash);
                }
            }

            tempTransactions = newTransactions;
        }

        return proof.toString();
    }

    private String applySHA256(String input) {
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA-256");
            byte[] hash = digest.digest(input.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                String hex = Integer.toHexString(0xff & b);
                if (hex.length() == 1) {
                    hexString.append('0');
                }
                hexString.append(hex);
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
            return "";
        }
    }
}

