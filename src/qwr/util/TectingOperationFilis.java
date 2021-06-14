package qwr.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.channels.FileChannel;
import java.nio.file.*;

public class TectingOperationFilis {

    public static void copyFile(File in, File out)
            throws IOException
    {
        FileChannel inChannel = new
                FileInputStream(in).getChannel();
        FileChannel outChannel = new
                FileOutputStream(out).getChannel();
        try {
            inChannel.transferTo(0, inChannel.size(),
                    outChannel);
        }
        catch (IOException e) {
            throw e;
        }
        finally {
            if (inChannel != null) inChannel.close();
            if (outChannel != null) outChannel.close();
        }
        //---------------------------
//--  for (File f : listOfFiles) { Files.copy(f.toPath(), new File("путь" + File.separator + f.getName()).toPath()); }

    }
    //------------------------------------------------------------------------
    private static void copyDir(String sourceDirName, String targetSourceDir) throws IOException {
        File folder = new File(sourceDirName);

        File[] listOfFiles = folder.listFiles();

        Path destDir = Paths.get(targetSourceDir);
        if (listOfFiles != null)
            for (File file : listOfFiles)
                Files.copy(file.toPath(), destDir.resolve(file.getName()), StandardCopyOption.REPLACE_EXISTING);
    }//copyDir
//=============================================================================================================
        //-----------------------------------------------------------------------------------------------
        private static long fileCopyUsingFileStreams(File fileToCopy, File newFile) throws IOException {//6800ms-полохо
            FileInputStream input = new FileInputStream(fileToCopy);
            FileOutputStream output = new FileOutputStream(newFile);
            byte[] buf = new byte[1024];
            int bytesRead;
            long start = System.currentTimeMillis();
            while ((bytesRead = input.read(buf)) > 0)
            {
                output.write(buf, 0, bytesRead);
            }
            long end = System.currentTimeMillis();

            input.close();
            output.close();

            return (end-start);
        }

        private static long fileCopyUsingNIOChannelClass(File fileToCopy, File newFile) throws IOException //1500ms-хорошо
        {
            FileInputStream inputStream = new FileInputStream(fileToCopy);
            FileChannel inChannel = inputStream.getChannel();

            FileOutputStream outputStream = new FileOutputStream(newFile);
            FileChannel outChannel = outputStream.getChannel();

            long start = System.currentTimeMillis();
            inChannel.transferTo(0, fileToCopy.length(), outChannel);
            long end = System.currentTimeMillis();

            inputStream.close();
            outputStream.close();

            return (end-start);
        }

        private static long fileCopyUsingApacheCommons(File fileToCopy, File newFile) throws IOException//3000ms-плохо
        {
            long start = System.currentTimeMillis();
//            FileUtils.copyFile(fileToCopy, newFile);
            long end = System.currentTimeMillis();
            return (end-start);
        }

        private static long fileCopyUsingNIOFilesClass(File fileToCopy, File newFile) throws IOException//1900ms-нормально
        {
            Path source = Paths.get(fileToCopy.getPath());
            Path destination = Paths.get(newFile.getPath());
            long start = System.currentTimeMillis();
            Files.copy(source, destination, StandardCopyOption.REPLACE_EXISTING);
            long end = System.currentTimeMillis();

            return (end-start);
        }

    //===================================================================================================
    public static void main(String[] args) {
        try {

            File tempFile = File.createTempFile("hello", ".tmp");
            System.out.println("Temp file On Default Location: " + tempFile.getAbsolutePath());
            tempFile = File.createTempFile("hello", ".tmp", new File("C:/"));
            System.out.println("Temp file On Specified Location: " + tempFile.getAbsolutePath());

        } catch (IOException e) {
            e.printStackTrace();
        }
        /**
         * Резульат
         *
         * Temp file On Default Location: C:\Users\swami\AppData\Local\Temp\hello7828748332363277400.tmp
         * Temp file On Specified Location: C:\hello950036450024130433.tmp
         */
        //===================================================================================================
        /**
         * Позволяет посмотреть элементы каталога, названия которых удовлетворяют переданному шаблону Glob.
         *
         * Например, следующий код возвращает список файлов с расширениями «.class», «.java», «.jar»:
         */

        Path dir = Path.of("c:\\windows");
        try (DirectoryStream<Path> stream =
                     Files.newDirectoryStream(dir, "*.{java,class,jar}")) {
            for (Path entry : stream) {
                System.out.println(entry.getFileName());
            }
        } catch (IOException x) {
            // IOException никогда не бросится во время итерации.
            // В этом куске кода оно может броситься только
            // методом newDirectoryStream
            System.err.println(x);
        }
//======================================================================================================
    }
}//TectingOperationFilis
