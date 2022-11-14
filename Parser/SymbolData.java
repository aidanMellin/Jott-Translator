package Parser;

public class SymbolData {

    public final String Name;
    public final String ReturnType;
    public final boolean IsMain;
    public final boolean IsInitialized;

    public SymbolData(String name, String returnType, boolean isMain, boolean isInitialized) {
        Name = name;
        ReturnType = returnType;
        IsMain = isMain;
        IsInitialized = isInitialized;
    }
}
