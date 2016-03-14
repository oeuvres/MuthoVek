package muthovek.fr.resource;

import java.io.File;
import java.util.LinkedList;


public abstract class ReadSourceFiles
{
    public final LinkedList<File> run(final File root)
    {
        final LinkedList<File> files;
        final long      size;
        final long      start;
        final long      end;
        final long      total;

        files = new LinkedList<File>();
        getFiles(root, files);
        start = System.currentTimeMillis();
        size = readFiles(files);
        end = System.currentTimeMillis();
        total = end - start;
        System.out.println(getClass().getName());
        System.out.println("time  = " + total);
        System.out.println("bytes = " + size);
    return files;
    }

    private void getFiles(final File dir, final LinkedList<File> files)
    {
        final File[] children;
       
        children = dir.listFiles();
        for(final File child : children)
        {
            if(child.isFile())
            {
                files.add(child);
            }
            else
            {
                getFiles(child, files);
            }
        }
    }

    private long readFiles(final LinkedList<File> files)
    {
        long size;
        size = 0;
        for(final File file : files)
        {
            size += readFile(file);
        }
        return (size);
    }
    protected abstract long readFile(File file);
    
}
