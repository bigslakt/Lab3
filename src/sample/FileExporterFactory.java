package sample;

public class FileExporterFactory {

    public static FileExporter createSaveFormat(String type)
    {
        if (type.equalsIgnoreCase("png")){
            return new PngExporter();
        }
        else if (type.equalsIgnoreCase("svg")){
            return new SvgExporter();
        }
        return null;
    }
}
