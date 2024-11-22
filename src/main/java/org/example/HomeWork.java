package org.example;


import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу Step из файла contest6_tasks.pdf
     */
    @SneakyThrows
    public void stepDanceValue(InputStream in, OutputStream out) {
        BufferedReader br = new BufferedReader(new InputStreamReader(in));
        String[] firstLine = br.readLine().split(" ");
        AVLTree tree = new AVLTree();
        int n = Integer.parseInt(firstLine[0]);
        int q = Integer.parseInt(firstLine[1]);

        for (int i = 1; i < n + 1; i++) {
            tree.add(i);
        }
        for (int i = 0; i < q; i++) {
            int x = Integer.parseInt(br.readLine());
            tree.update(x);
            out.write(String.valueOf(tree.getMaxLength()).getBytes());
            if (i < q - 1) {
                out.write("\n".getBytes());
            }
        }


    }
}
