package me.az1.dblab.Common.DatabaseControllerCorbaPackage;


/**
* me/az1/dblab/Common/DatabaseControllerCorbaPackage/int32Helper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/java/me/az1/dblab/Common/DatabaseControllerCorba.idl
* Thursday, December 4, 2014 5:40:32 PM EET
*/

abstract public class int32Helper
{
  private static String  _id = "IDL:me/az1/dblab/Common/DatabaseControllerCorba/int32:1.0";

  public static void insert (org.omg.CORBA.Any a, int that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static int extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_long);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (me.az1.dblab.Common.DatabaseControllerCorbaPackage.int32Helper.id (), "int32", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static int read (org.omg.CORBA.portable.InputStream istream)
  {
    int value = (int)0;
    value = istream.read_long ();
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, int value)
  {
    ostream.write_long (value);
  }

}
