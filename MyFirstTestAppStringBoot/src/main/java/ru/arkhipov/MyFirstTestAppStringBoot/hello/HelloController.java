package ru.arkhipov.MyFirstTestAppStringBoot.hello;

import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;

@RestController
public class HelloController {

    // Существующий hello()
    @GetMapping("/hello")
    public String hello(@RequestParam(value = "name", defaultValue = "World") String name) {
        return String.format("Hello %s!", name);
    }

    // Коллекции
    private final ArrayList<String> arrayList = new ArrayList<>();
    private final HashMap<Integer, String> hashMap = new HashMap<>();
    private int mapCounter = 0;

    // 1. updateArrayList
    @GetMapping("/update-array")
    public String updateArrayList(@RequestParam String value) {
        arrayList.add(value);
        return "Добавлено в ArrayList: " + value;
    }

    // 2. showArrayList
    @GetMapping("/show-array")
    public ArrayList<String> showArrayList() {
        return arrayList;
    }

    // 3. updateHashMap
    @GetMapping("/update-map")
    public String updateHashMap(@RequestParam String value) {
        mapCounter++;
        hashMap.put(mapCounter, value);
        return "Добавлено в HashMap с ключом " + mapCounter + ": " + value;
    }

    // 4. showHashMap
    @GetMapping("/show-map")
    public HashMap<Integer, String> showHashMap() {
        return hashMap;
    }

    // 5. showAllLenght
    @GetMapping("/show-all-lenght")
    public String showAllLength() {
        return "ArrayList содержит: " + arrayList.size() +
                " элементов, HashMap содержит: " + hashMap.size() + " элементов.";
    }
}
