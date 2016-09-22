package com.couchbaseupdatedocumenttest;

import com.couchbase.lite.CouchbaseLiteException;
import com.couchbase.lite.Database;
import com.couchbase.lite.Document;
import com.couchbase.lite.UnsavedRevision;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Random;
import java.util.UUID;

public class DocumentUpdateUtils {

    private static ObjectMapper mapper;

    public static void add1ToDoc(Database database) {
        Document doc = database.getDocument("_local/mydoc");
        final String id = UUID.randomUUID().toString();
        final PersonPOJO p = new PersonPOJO(id, generateString(), generateString(), generateString(), generateString(),
                generateString());
        try {
            doc.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    Map<String, Object> propertiesList = newRevision.getUserProperties();
                    Map<String, Object> propertiesItem = getAllOldItems(propertiesList);
                    if (propertiesItem == null)
                        propertiesItem = new HashMap<>();
                    propertiesItem.put(id, p);
                    propertiesList.put("people", propertiesItem);
                    newRevision.setUserProperties(propertiesList);
                    return true;
                }
            });
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    private static String generateString() {
        String characters = "abcdefghijklmnopqrstuvwxyz";
        char[] text = new char[20];
        for (int i = 0; i < 20; i++)
            text[i] = characters.charAt(new Random().nextInt(characters.length()));
        return new String(text);
    }

    private static Map<String, Object> getAllOldItems(Map<String, Object> properties) {
        return (HashMap) properties.get("people");
    }

    public static void add100ToDoc(Database database) {
        Document doc = database.getDocument("_local/mydoc");
        final Map<String, Object> newEntries = new HashMap<>(100);
        for (int i = 0; i < 100; i++) {
            String id = UUID.randomUUID().toString();
            PersonPOJO p = new PersonPOJO(id, generateString(), generateString(), generateString(), generateString(),
                    generateString());
            newEntries.put(id, p);
        }
        try {
            doc.update(new Document.DocumentUpdater() {
                @Override
                public boolean update(UnsavedRevision newRevision) {
                    Map<String, Object> propertiesList = newRevision.getUserProperties();
                    Map<String, Object> propertiesItem = getAllOldItems(propertiesList);
                    if (propertiesItem == null)
                        propertiesItem = new HashMap<>();
                    propertiesItem.putAll(newEntries);
                    propertiesList.put("people", propertiesItem);
                    newRevision.setUserProperties(propertiesList);
                    return true;
                }
            });
        } catch (CouchbaseLiteException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<PersonPOJO> getNoOfPeople(Database database) {
        ArrayList<PersonPOJO> items = new ArrayList<>();
        Document doc = database.getDocument("_local/mydoc");
        Map<String, Object> map = getAllOldItems(doc.getProperties());
        if (map == null)
            return items;
        for (String key : map.keySet())
            items.add(parseObject(map, key));
        return items;
    }

    private static PersonPOJO parseObject(Map<String, Object> map, String key) {
        if (mapper == null)
            mapper = new ObjectMapper();
        if (map.get(key) instanceof LinkedHashMap)
            return mapper.convertValue(map.get(key), PersonPOJO.class);
        else
            return new PersonPOJO(mapper.convertValue(map.get(key), PersonPOJO.class));
    }
}
