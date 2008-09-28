/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package org.catacombae.hfsexplorer.types.hfscommon;

import java.io.PrintStream;
import org.catacombae.hfsexplorer.types.hfsplus.HFSPlusCatalogKey;
import org.catacombae.hfsexplorer.types.hfsplus.HFSPlusCatalogFile;
import org.catacombae.hfsexplorer.types.hfs.CatKeyRec;
import org.catacombae.hfsexplorer.types.hfs.CdrFilRec;

/**
 *
 * @author erik
 */
public abstract class CommonHFSCatalogFileRecord extends CommonHFSCatalogLeafRecord {
    protected CommonHFSCatalogKey key;
    protected CommonHFSCatalogFile data;
    
    private CommonHFSCatalogFileRecord(CommonHFSCatalogKey key,
            CommonHFSCatalogFile data) {
        this.key = key;
        this.data = data;
    }
    
    @Override
    public CommonHFSCatalogKey getKey() {
        return key;
    }

    public CommonHFSCatalogFile getData() {
        return data;
    }

    @Override
    public void print(PrintStream ps, String prefix) {
        ps.println(prefix + "CommonHFSCatalogFileRecord:");
        printFields(ps, prefix + " ");
    }

    public void printFields(PrintStream ps, String prefix) {
        ps.println(prefix + "key:");
        key.print(ps, prefix + " ");
        ps.println(prefix + "data:");
        data.print(ps, prefix + " ");
    }

    public static CommonHFSCatalogFileRecord create(HFSPlusCatalogKey key,
            HFSPlusCatalogFile data) {
        return new HFSPlusImplementation(key, data);
    }
    
    public static CommonHFSCatalogFileRecord create(CatKeyRec key, CdrFilRec data) {
        return new HFSImplementation(key, data);
    }
    
    public static class HFSImplementation extends CommonHFSCatalogFileRecord {
        public HFSImplementation(CatKeyRec key, CdrFilRec data) {
            super(CommonHFSCatalogKey.create(key), CommonHFSCatalogFile.create(data));
        }
        
        protected HFSImplementation(CommonHFSCatalogKey key,
            CommonHFSCatalogFile data) {
            super(key, data);
        }
        
        @Override
        public int getSize() {
            return key.occupiedSize() + data.size();
        }

        @Override
        public byte[] getBytes() {
            byte[] result = new byte[getSize()];
            byte[] tempData;
            int offset = 0;

            tempData = key.getBytes();
            System.arraycopy(tempData, 0, result, offset, tempData.length); offset += tempData.length;
            tempData = data.getBytes();
            System.arraycopy(tempData, 0, result, offset, tempData.length); offset += tempData.length;
            
            return result;
        }
    }
    
    public static class HFSPlusImplementation extends HFSImplementation {
        public HFSPlusImplementation(HFSPlusCatalogKey key, HFSPlusCatalogFile data) {
            super(CommonHFSCatalogKey.create(key), CommonHFSCatalogFile.create(data));
        }
    }
}