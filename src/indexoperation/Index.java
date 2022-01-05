package indexoperation;

import gitobject.Blob;
import jitinitiation.JitInitiation;
import serialization.ZipSerial;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class Index
{


    private File indexFile;
    private Map<String, String> indexMap;

    public Index () throws Exception
    {
        this.indexFile = new File( ".jit" + File.separator + "index" + File.separator +"trackedBlob" );
        ObjectInputStream oi = new ObjectInputStream(new FileInputStream(this.indexFile));
        this.indexMap = (Map<String, String>)oi.readObject();
        oi.close();
    }

    /**
     * 初始化index区
     */
    public static void initIndex()
    {
        try
        {
            //初始化生成暂存区内序列化的hashMap，键为相对路径加文件名，值为其在“.jit/objects”文件夹中对应文件名，文件名叫“trackedBlob”
            Map<String,String> testmap = new HashMap<>();
            File indexFile = new File( ".jit" + File.separator + "index" + File.separator +"trackedBlob" );
            ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(indexFile));
            oo.writeObject(testmap);
            oo.flush();
            oo.close();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
    }

    /**
     * d将当前暂存区中的indexmap反序列化取出x
     * @return
     * @throws Exception
     */
    public static Map<String, String> getIndexMap() throws Exception
    {
        Map<String, String> trackedMap = new Index().indexMap;
        return trackedMap;
    }

    /**
     * 将传入的Map更新到index中
     * @param trackedMap
     * @throws Exception
     */
    public static void setIndexMap(Map<String, String> trackedMap) throws Exception
    {
        File trackedBlob = new File(".jit"+File.separator+"index"+File.separator+"trackedBlob");
        ObjectOutputStream oo = new ObjectOutputStream(new FileOutputStream(trackedBlob));
        oo.writeObject(trackedMap);
        oo.flush();
        oo.close();
    }

    public static void recoverWork(String recoverPath) throws Exception
    {
        Map<String, String> indexMap = Index.getIndexMap();
        indexMap.forEach((relevantPath,serialFileName) ->
        {
            try { Blob.deserialize(serialFileName).toFile(recoverPath);}
            catch (Exception e) { e.printStackTrace();}
        });
    }



}
