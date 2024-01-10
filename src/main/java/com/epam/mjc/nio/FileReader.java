package com.epam.mjc.nio;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.file.Path;
import java.nio.file.Paths;


public class FileReader {

    public Profile getDataFromFile(File file) {

        String data = "";
        String[] arr;
        String name;
        Integer age;
        String email;
        Long phone;

        Path path = Paths.get(file.getPath());

        try (RandomAccessFile aFile = new RandomAccessFile(path.toString(), "r");
             FileChannel inChannel = aFile.getChannel()) {

            ByteBuffer buffer = ByteBuffer.allocate(1024);

            while (inChannel.read(buffer) > 0) {
                buffer.flip();
                for (int i = 0; i < buffer.limit(); i++) {
                    System.out.print((char) buffer.get());
                }
                buffer.clear(); // do something with the data and clear/compact it.
            }

            for (int i=0; i<buffer.limit(); i++) {
                data += (char) buffer.get();
            }

            arr = data.split("\n");

            for (int i=0; i<arr.length; i++) {
                int colon = arr[i].indexOf(":");
                arr[i] = arr[i].substring(colon+2).trim();
            }

            name = arr[0];
            age = Integer.parseInt(arr[1]);
            email = arr[2];
            phone = Long.parseLong(arr[3]);

            return new Profile(name, age, email, phone);

        } catch (IOException e) {
            e.printStackTrace();
        }

        return new Profile();
    }
    public static void main(String[] args) {

        String sPath = "C:\\Users\\Azimbek\\IdeaProjects\\stage1-module7-nio-task1\\src\\main\\resources\\Profile.txt";

        FileReader fileReader = new FileReader();

        fileReader.getDataFromFile(new File(sPath));
    }
}
