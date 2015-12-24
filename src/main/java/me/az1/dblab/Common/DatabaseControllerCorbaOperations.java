package me.az1.dblab.Common;


/**
* me/az1/dblab/Common/DatabaseControllerCorbaOperations.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/java/me/az1/dblab/Common/DatabaseControllerCorba.idl
* Thursday, December 4, 2014 5:40:32 PM EET
*/

public interface DatabaseControllerCorbaOperations 
{
  boolean IsOpened ();
  void NewDatabase ();
  void LoadDatabase (String path);
  void SaveDatabase (String path);
  void CloseDatabase ();
  int[][] DatabaseGetTableVersions ();
  int[] DatabaseAddEmptyTable (String scheme, String name);
  boolean DatabaseRemoveTable (int[] version);
  int[][] DatabaseGetTableRowVersions (int[] version);
  int DatabaseSize ();
  int DatabaseTableSize (int[] version);
  String DatabaseTableName (int[] version);
  int DatabaseTableRowLength (int[] version);
  int[] DatabaseTableAddEmptyRow (int[] version);
  boolean DatabaseTableRemoveRow (int[] tableVersion, int[] rowVersion);
  byte[] DatabaseTableGetField (int[] tableVersion, int[] rowVersion, int column);
  String DatabaseTableGetFieldString (int[] tableVersion, int[] rowVersion, int column);
  void DatabaseTableSetFieldData (int[] tableVersion, int[] rowVersion, int column, byte[] value);
  void DatabaseTableSetFieldString (int[] tableVersion, int[] rowVersion, int column, String strValue);
  int[] DatabaseTableFind (int[] tableVersion, String pattern);
  int[] DatabaseTableRemoveDuplicates (int[] tableVersion);
} // interface DatabaseControllerCorbaOperations
