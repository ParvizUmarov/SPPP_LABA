package org.example.service;

import org.example.bot.IOService;
import org.springframework.stereotype.Service;

import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOServiceImpl implements IOService {
    private final Scanner scanner;
    private final PrintWriter writer;

    public IOServiceImpl(InputStream inputStream, OutputStream outputStream) {
        this.scanner = new Scanner(inputStream);
        this.writer = new PrintWriter(outputStream);
    }

    @Override
    public void print(String str) {
        writer.print(str);
        writer.flush();
    }

    @Override
    public void println(String str) {
        writer.println(str);
        writer.flush();
    }

    @Override
    public String readLine() {
        return scanner.nextLine();
    }
}
