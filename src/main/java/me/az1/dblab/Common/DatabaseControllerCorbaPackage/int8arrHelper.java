package me.az1.dblab.Common.DatabaseControllerCorbaPackage;


/**
* me/az1/dblab/Common/DatabaseControllerCorbaPackage/int8arrHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/java/me/az1/dblab/Common/DatabaseControllerCorba.idl
* Thursday, December 4, 2014 5:40:32 PM EET
*/

abstract public class int8arrHelper
{
  private static String  _id = "IDL:me/az1/dblab/Common/DatabaseControllerCorba/int8arr:1.0";

  public static void insert (org.omg.CORBA.Any a, byte[] that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static byte[] extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().get_primitive_tc (org.omg.CORBA.TCKind.tk_octet);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (me.az1.dblab.Common.DatabaseControllerCorbaPackage.int8Helper.id (), "int8", __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_sequence_tc (0, __typeCode);
      __typeCode = org.omg.CORBA.ORB.init ().create_alias_tc (me.az1.dblab.Common.DatabaseControllerCorbaPackage.int8arrHelper.id (), "int8arr", __typeCode);
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static byte[] read (org.omg.CORBA.portable.InputStream istream)
  {
    byte value[] = null;
    int _len0 = istream.read_long ();
    value = new byte[_len0];
    for (int _o1 = 0;_o1 < value.length; ++_o1)
      value[_o1] = me.az1.dblab.Common.DatabaseControllerCorbaPackage.int8Helper.read (istream);
    return value;
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, byte[] value)
  {
    ostream.write_long (value.length);
    for (int _i0 = 0;_i0 < value.length; ++_i0)
      me.az1.dblab.Common.DatabaseControllerCorbaPackage.int8Helper.write (ostream, value[_i0]);
  }

}
