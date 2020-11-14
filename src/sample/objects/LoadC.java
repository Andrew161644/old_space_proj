package sample.objects;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class LoadC {
    List<SpaceObject> spaceObjects=new ArrayList<>();

    public LoadC(List<SpaceObject> spaceObjects) {
        this.spaceObjects = spaceObjects;
    }
    public void save(FileWriter writer) throws IOException {
        for(int i=0;i< spaceObjects.size();i++){
            spaceObjects.get(i).save(writer);
        }
        writer.close();
    }
    public List<SpaceObject> laod(FileReader reader) throws IOException {
        spaceObjects=new ArrayList<>();
        for(int i=0;i<9;i++){

            String s="";
            int c=0;
            while (true){
                c=reader.read();
                if(c=='\n' || c==-1)
                    break;
                s+=(char)c;
            }
            SpaceObject object=new SpaceObject();
            object.load(s);
            spaceObjects.add(object);
        }
        System.out.println(spaceObjects.size());
        return spaceObjects;
    }
}
