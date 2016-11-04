package ru.chernykh.io;

import apple.laf.JRSUIUtils;

import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.regex.Pattern;

/**
 * Created by mikhail on 04.11.16.
 * Создание последовательности объектов File, соответствующих
 * регулярному выражению - либо в локальном каталоге, либо directory
 * посредством обхода дерева каталогов
 */
public final class Directory {
    //Список файлов в директории
    public static File[] local (File dir, final String regex) {

        return dir.listFiles(new FilenameFilter() {
            private Pattern pattern = Pattern.compile(regex);
            @Override
            public boolean accept(File dir, String name) {
                //Создаем новый файл по имени и возвращаем его, если он удовлетворяет регулярному выражению
                return pattern.matcher(new File(name).getName()).matches();
            }
        });

    }
    //Список файлов в директории по пути
    public static File [] local (String path, final String regex) {
        return local (new File(path), regex);
    }

    //Кортеж для возвращения пары объетов
    public static class TreeInfo implements Iterable<File> {

        public List<File> files = new ArrayList<File>();
        public List<File> dirs = new ArrayList<File>();

        @Override
        public Iterator<File> iterator() {
            return files.iterator();
        }

        void addAll(TreeInfo other) {
            files.addAll(other.files);
            dirs.addAll(other.dirs);
        }

        public String toString() {
            return "dirs: " + PPrint.pformat(dirs) + "\n\nfiles: "+PPrint.pformat(files);
        }


    }
    //от имени файла
    public static TreeInfo walk (String start, String regex) {
        return recursiveDirs(new File(start), regex);
    }
    //по самому файлу
    public static TreeInfo walk (File start, String regex) {
        return recursiveDirs(start, regex);
    }
    //выводим все
    public  static TreeInfo walk(File start) { //Все выводим
        return recursiveDirs(start, ".*");
    }

    //выводим все
    public  static TreeInfo walk(String start) { //Все выводим
        return recursiveDirs(new File(start), ".*");
    }

    private static TreeInfo recursiveDirs(File startDir, String regex) {
        TreeInfo result = new TreeInfo();
        for(File item: startDir.listFiles()) {
            if(item.isDirectory()) {
                result.dirs.add(item);
                //Все файлы внутри директории
                result.addAll(recursiveDirs(item,regex));
            }
            else {
                if(item.getName().matches(regex))
                    result.files.add(item);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        if(args.length == 0) {
            System.out.println(walk("."));
        } else
            for (String arg: args
                 ) {
                System.out.println(walk(arg));
            }
    }
}
