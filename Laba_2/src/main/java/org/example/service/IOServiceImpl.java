package org.example.service;

import org.example.aop.Analyze;
import org.example.bot.IOService;
import org.springframework.stereotype.Service;
import java.io.PrintWriter;
import java.util.Scanner;

@Service
@Analyze
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
