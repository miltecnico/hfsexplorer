/*-
 * Copyright (C) 2006 Erik Larsson
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package org.catacombae.hfs.types.hfsplus;

import org.catacombae.csjc.structelements.Dictionary;
import org.catacombae.util.Util;
import java.io.PrintStream;
import org.catacombae.csjc.PrintableStruct;
import org.catacombae.csjc.StructElements;
import org.catacombae.csjc.structelements.StringRepresentableField;

/** This class was generated by CStructToJavaClass. */
public class HFSPlusBSDInfo implements PrintableStruct, StructElements {
    /*
     * struct HFSPlusBSDInfo
     * size: 16 bytes
     * description:
     *
     * BP  Size  Type    Identifier  Description
     * -----------------------------------------
     * 0   4     UInt32  ownerID
     * 4   4     UInt32  groupID
     * 8   1     UInt8   adminFlags
     * 9   1     UInt8   ownerFlags
     * 10  2     UInt16  fileMode
     * 12  4     UInt32  special
     */
    public static final byte MASK_ADMIN_ARCHIVED = 0x1;
    public static final byte MASK_ADMIN_IMMUTABLE = 0x2;
    public static final byte MASK_ADMIN_APPEND = 0x4;
    public static final byte MASK_OWNER_NODUMP = 0x1;
    public static final byte MASK_OWNER_IMMUTABLE = 0x2;
    public static final byte MASK_OWNER_APPEND = 0x4;
    public static final byte MASK_OWNER_OPAQUE = 0x8;
    public static final byte FILETYPE_UNDEFINED = 00;
    public static final byte FILETYPE_FIFO = 01;
    public static final byte FILETYPE_CHARACTER_SPECIAL = 02;
    public static final byte FILETYPE_DIRECTORY = 04;
    public static final byte FILETYPE_BLOCK_SPECIAL = 06;
    public static final byte FILETYPE_REGULAR = 010;
    public static final byte FILETYPE_SYMBOLIC_LINK = 012;
    public static final byte FILETYPE_SOCKET = 014;
    public static final byte FILETYPE_WHITEOUT = 016;


    private final byte[] ownerID = new byte[4];
    private final byte[] groupID = new byte[4];
    private final byte[] adminFlags = new byte[1];
    private final byte[] ownerFlags = new byte[1];
    private final byte[] fileMode = new byte[2];
    private final byte[] special = new byte[4];

    public HFSPlusBSDInfo(byte[] data, int offset) {
	System.arraycopy(data, offset+0, ownerID, 0, 4);
	System.arraycopy(data, offset+4, groupID, 0, 4);
	System.arraycopy(data, offset+8, adminFlags, 0, 1);
	System.arraycopy(data, offset+9, ownerFlags, 0, 1);
	System.arraycopy(data, offset+10, fileMode, 0, 2);
	System.arraycopy(data, offset+12, special, 0, 4);
    }

    public static int length() { return 16; }

    public int getOwnerID() { return Util.readIntBE(ownerID); }
    public int getGroupID() { return Util.readIntBE(groupID); }
    public byte getAdminFlags() { return Util.readByteBE(adminFlags); }
    public byte getOwnerFlags() { return Util.readByteBE(ownerFlags); }
    public short getFileMode() { return Util.readShortBE(fileMode); }
    public int getSpecial() { return Util.readIntBE(special); }

    public boolean getAdminArchivedFlag()  { return (getAdminFlags() & MASK_ADMIN_ARCHIVED)  != 0; }
    public boolean getAdminImmutableFlag() { return (getAdminFlags() & MASK_ADMIN_IMMUTABLE) != 0; }
    public boolean getAdminAppendFlag()    { return (getAdminFlags() & MASK_ADMIN_APPEND)    != 0; }

    public boolean getOwnerNodumpFlag()    { return (getOwnerFlags() & MASK_OWNER_NODUMP)    != 0; }
    public boolean getOwnerImmutableFlag() { return (getOwnerFlags() & MASK_OWNER_IMMUTABLE) != 0; }
    public boolean getOwnerAppendFlag()    { return (getOwnerFlags() & MASK_OWNER_APPEND)    != 0; }
    public boolean getOwnerOpaqueFlag()    { return (getOwnerFlags() & MASK_OWNER_OPAQUE)    != 0; }

    public byte getFileModeFileType() {
	int type = (getFileMode() >> 12) & 017;
	return (byte)type;
    }

    public boolean getFileModeSetUserID()    { return ((getFileMode() >> 9) & 0x4) != 0; }
    public boolean getFileModeSetGroupID()   { return ((getFileMode() >> 9) & 0x2) != 0; }
    public boolean getFileModeSticky()       { return ((getFileMode() >> 9) & 0x1) != 0; }
    public boolean getFileModeOwnerRead()    { return ((getFileMode() >> 6) & 0x4) != 0; }
    public boolean getFileModeOwnerWrite()   { return ((getFileMode() >> 6) & 0x2) != 0; }
    public boolean getFileModeOwnerExecute() { return ((getFileMode() >> 6) & 0x1) != 0; }
    public boolean getFileModeGroupRead()    { return ((getFileMode() >> 3) & 0x4) != 0; }
    public boolean getFileModeGroupWrite()   { return ((getFileMode() >> 3) & 0x2) != 0; }
    public boolean getFileModeGroupExecute() { return ((getFileMode() >> 3) & 0x1) != 0; }
    public boolean getFileModeOtherRead()    { return ((getFileMode() >> 0) & 0x4) != 0; }
    public boolean getFileModeOtherWrite()   { return ((getFileMode() >> 0) & 0x2) != 0; }
    public boolean getFileModeOtherExecute() { return ((getFileMode() >> 0) & 0x1) != 0; }
    //public boolean getFileMode() {}

    /**
     * Returns the POSIX-type file mode string for this file, as it would appear
     * when listing it with 'ls -l'. Example: <code>drwxr-x---</code>.
     *
     * @return the POSIX-type file mode string for this file.
     */
    public String getFileModeString() {
        String result;
        byte fileType = getFileModeFileType();
        switch(fileType) {
            case FILETYPE_UNDEFINED: // This one appears at the root node (CNID 2) sometimes. dunno what it would look like in ls -l
                result = "?";
                break;
            case FILETYPE_FIFO:
                result = "p";
                break;
            case FILETYPE_CHARACTER_SPECIAL:
                result = "c";
                break;
            case FILETYPE_DIRECTORY:
                result = "d";
                break;
            case FILETYPE_BLOCK_SPECIAL:
                result = "b";
                break;
            case FILETYPE_REGULAR:
                result = "-";
                break;
            case FILETYPE_SYMBOLIC_LINK:
                result = "l";
                break;
            case FILETYPE_SOCKET:
                result = "s";
                break;
            case FILETYPE_WHITEOUT:
                result = "w";
                break; // How does this appear in "ls -l" ? and what is it?
            default:
                throw new RuntimeException("Unknown file type (read: " + fileType + " REGULAR: " + FILETYPE_REGULAR + " MODE: 0x" + Util.toHexStringBE(getFileMode()) + ")!");
        }

        if(getFileModeOwnerRead())
            result += "r";
        else
            result += "-";
        if(getFileModeOwnerWrite())
            result += "w";
        else
            result += "-";
        if(getFileModeOwnerExecute()) {
            if(getFileModeSetUserID())
                result += "s";
            else
                result += "x";
        }
        else {
            if(getFileModeSetUserID())
                result += "S";
            else
                result += "-";
        }
        if(getFileModeGroupRead())
            result += "r";
        else
            result += "-";
        if(getFileModeGroupWrite())
            result += "w";
        else
            result += "-";
        if(getFileModeGroupExecute()) {
            if(getFileModeSetGroupID())
                result += "s";
            else
                result += "x";
        }
        else {
            if(getFileModeSetGroupID())
                result += "S";
            else
                result += "-";
        }
        if(getFileModeOtherRead())
            result += "r";
        else
            result += "-";
        if(getFileModeOtherWrite())
            result += "w";
        else
            result += "-";
        if(getFileModeOtherExecute()) {
            if(getFileModeSticky())
                result += "t";
            else
                result += "x";
        }
        else {
            if(getFileModeSticky())
                result += "T";
            else
                result += "-";
        }

        return result;
    }

    public void printFields(PrintStream ps, String prefix) {
        ps.println(prefix + " ownerID: " + getOwnerID());
        ps.println(prefix + " groupID: " + getGroupID());
        ps.println(prefix + " adminFlags: " + getAdminFlags());
        ps.println(prefix + " ownerFlags: " + getOwnerFlags());
        ps.println(prefix + " fileMode: " + getFileMode());
        ps.println(prefix + " special: " + getSpecial());
    }

    public void print(PrintStream ps, String prefix) {
        ps.println(prefix + "HFSPlusBSDInfo:");
        printFields(ps, prefix);
    }

    byte[] getBytes() {
        byte[] result = new byte[length()];
        int offset = 0;

        System.arraycopy(ownerID, 0, result, offset, ownerID.length); offset += ownerID.length;
        System.arraycopy(groupID, 0, result, offset, groupID.length); offset += groupID.length;
        System.arraycopy(adminFlags, 0, result, offset, adminFlags.length); offset += adminFlags.length;
        System.arraycopy(ownerFlags, 0, result, offset, ownerFlags.length); offset += ownerFlags.length;
        System.arraycopy(fileMode, 0, result, offset, fileMode.length); offset += fileMode.length;
        System.arraycopy(special, 0, result, offset, special.length); offset += special.length;

        return result;
    }

    /* @Override */
    public Dictionary getStructElements() {
        DictionaryBuilder db = new DictionaryBuilder(HFSPlusBSDInfo.class.getSimpleName());

        db.addUIntBE("ownerID", ownerID);
        db.addUIntBE("groupID", groupID);

        final Dictionary adminFlagsDict;
        {
            DictionaryBuilder dbAdminFlags = new DictionaryBuilder("UInt8");
            dbAdminFlags.addFlag("append", adminFlags, 2, "Writes to file may only append");
            dbAdminFlags.addFlag("immutable", adminFlags, 1, "File may not be changed");
            dbAdminFlags.addFlag("archived", adminFlags, 0, "File has been archived");
            adminFlagsDict = dbAdminFlags.getResult();
        }
        db.add("adminFlags", adminFlagsDict);

        final Dictionary ownerFlagsDict;
        {
            DictionaryBuilder dbOwnerFlags = new DictionaryBuilder("UInt8");
            dbOwnerFlags.addFlag("opaque", ownerFlags, 3, "Directory is opaque");
            dbOwnerFlags.addFlag("append", ownerFlags, 2, "Writes to file may only append");
            dbOwnerFlags.addFlag("immutable", ownerFlags, 1, "File may not be changed");
            dbOwnerFlags.addFlag("nodump", ownerFlags, 0, "Do not dump (backup or archive) this file");
            ownerFlagsDict = dbOwnerFlags.getResult();
        }
        db.add("ownerFlags", ownerFlagsDict);

       final Dictionary fileModeFlagsDict;
        {
            DictionaryBuilder dbFileModeFlags = new DictionaryBuilder("UInt16");

            dbFileModeFlags.add("fileType", new FileTypeField());
            dbFileModeFlags.addFlag("setUserID", fileMode, 11, "Set user ID on execution");
            dbFileModeFlags.addFlag("setGroupID", fileMode, 10, "Set group ID on execution");
            dbFileModeFlags.addFlag("sticky", fileMode, 9, "Sticky bit");
            dbFileModeFlags.addFlag("ownerRead", fileMode, 8, "Owner can read");
            dbFileModeFlags.addFlag("ownerWrite", fileMode, 7, "Owner can write");
            dbFileModeFlags.addFlag("ownerExecute", fileMode, 6, "Owner can execute");
            dbFileModeFlags.addFlag("groupRead", fileMode, 5, "Group can read");
            dbFileModeFlags.addFlag("groupWrite", fileMode, 4, "Group can write");
            dbFileModeFlags.addFlag("groupExecute", fileMode, 3, "Group can execute");
            dbFileModeFlags.addFlag("otherRead", fileMode, 2, "Others can read");
            dbFileModeFlags.addFlag("otherWrite", fileMode, 1, "Others can write");
            dbFileModeFlags.addFlag("otherExecute", fileMode, 0, "Others can execute");

            fileModeFlagsDict = dbFileModeFlags.getResult();
        }
        db.add("fileMode", fileModeFlagsDict);

        db.addUIntBE("special", special);

        return db.getResult();
    }

    private class FileTypeField extends StringRepresentableField {
        public FileTypeField() {
            super("FileType", ASCIISTRING);
        }

        @Override
        public String getValueAsString() {
            byte fileTypeByte = getFileModeFileType();
            switch(fileTypeByte) {
                case FILETYPE_UNDEFINED: // This one appears at the root node (CNID 2) sometimes. dunno what it would look like in ls -l
                    return "Undefined";
                case FILETYPE_FIFO:
                    return "FIFO";
                case FILETYPE_CHARACTER_SPECIAL:
                    return "Character special file";
                case FILETYPE_DIRECTORY:
                    return "Directory";
                case FILETYPE_BLOCK_SPECIAL:
                    return "Block special file";
                case FILETYPE_REGULAR:
                    return "Regular file";
                case FILETYPE_SYMBOLIC_LINK:
                    return "Symbolic link";
                case FILETYPE_SOCKET:
                    return "Socket";
                case FILETYPE_WHITEOUT:
                    return "Whiteout";
                default:
                    return "[Unknown file type: " + fileTypeByte + "]";
            }
        }

        @Override
        public void setStringValue(String value) throws IllegalArgumentException {
            throw new UnsupportedOperationException("Not supported yet.");
        }

        @Override
        public String validateStringValue(String s) {
            throw new UnsupportedOperationException("Not supported yet.");
        }
    }
}
