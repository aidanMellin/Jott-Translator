package Parser;

import java.util.ArrayList;

public class SymbolData {

    public String Name;
    public String ReturnType;
    public boolean IsMain;
    public boolean IsInitialized;
    public boolean IsFunction;
    public ArrayList<String> Params;
    public ArrayList<String> ParamsTypes;

    public SymbolData(String name, String returnType, boolean isMain, boolean isInitialized, boolean isFunction, ArrayList<String> params, ArrayList<String> paramsTypes) {
        Name = name;
        ReturnType = returnType;
        IsMain = isMain;
        IsInitialized = isInitialized;
        IsFunction = isFunction;
        Params = params;
        ParamsTypes = paramsTypes;
    }
}
