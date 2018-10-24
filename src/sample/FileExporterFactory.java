package sample;

//The class FileExporterFactory is used to create either an PngExporter or an SvgExporter
public class FileExporterFactory {

    //The method FileExporter takes a String telling which exporter to create and returns it
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
