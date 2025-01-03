package org.example.service.impl;

import org.example.service.IOService;
import org.springframework.stereotype.Service;

import java.io.PrintWriter;
import java.util.Scanner;

@Service
public class IOServiceImpl implements IOService {
    private final Scanner scanner;
    private final PrintWriter writer;

    public IOServiceImpl() {
        this.scanner = new Scanner(System.in);
        this.writer = new PrintWriter(System.out);
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
