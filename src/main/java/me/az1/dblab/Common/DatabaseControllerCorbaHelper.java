package me.az1.dblab.Common;


/**
* me/az1/dblab/Common/DatabaseControllerCorbaHelper.java .
* Generated by the IDL-to-Java compiler (portable), version "3.2"
* from src/main/java/me/az1/dblab/Common/DatabaseControllerCorba.idl
* Thursday, December 4, 2014 5:40:32 PM EET
*/

abstract public class DatabaseControllerCorbaHelper
{
  private static String  _id = "IDL:me/az1/dblab/Common/DatabaseControllerCorba:1.0";

  public static void insert (org.omg.CORBA.Any a, me.az1.dblab.Common.DatabaseControllerCorba that)
  {
    org.omg.CORBA.portable.OutputStream out = a.create_output_stream ();
    a.type (type ());
    write (out, that);
    a.read_value (out.create_input_stream (), type ());
  }

  public static me.az1.dblab.Common.DatabaseControllerCorba extract (org.omg.CORBA.Any a)
  {
    return read (a.create_input_stream ());
  }

  private static org.omg.CORBA.TypeCode __typeCode = null;
  synchronized public static org.omg.CORBA.TypeCode type ()
  {
    if (__typeCode == null)
    {
      __typeCode = org.omg.CORBA.ORB.init ().create_interface_tc (me.az1.dblab.Common.DatabaseControllerCorbaHelper.id (), "DatabaseControllerCorba");
    }
    return __typeCode;
  }

  public static String id ()
  {
    return _id;
  }

  public static me.az1.dblab.Common.DatabaseControllerCorba read (org.omg.CORBA.portable.InputStream istream)
  {
    return narrow (istream.read_Object (_DatabaseControllerCorbaStub.class));
  }

  public static void write (org.omg.CORBA.portable.OutputStream ostream, me.az1.dblab.Common.DatabaseControllerCorba value)
  {
    ostream.write_Object ((org.omg.CORBA.Object) value);
  }

  public static me.az1.dblab.Common.DatabaseControllerCorba narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof me.az1.dblab.Common.DatabaseControllerCorba)
      return (me.az1.dblab.Common.DatabaseControllerCorba)obj;
    else if (!obj._is_a (id ()))
      throw new org.omg.CORBA.BAD_PARAM ();
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      me.az1.dblab.Common._DatabaseControllerCorbaStub stub = new me.az1.dblab.Common._DatabaseControllerCorbaStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

  public static me.az1.dblab.Common.DatabaseControllerCorba unchecked_narrow (org.omg.CORBA.Object obj)
  {
    if (obj == null)
      return null;
    else if (obj instanceof me.az1.dblab.Common.DatabaseControllerCorba)
      return (me.az1.dblab.Common.DatabaseControllerCorba)obj;
    else
    {
      org.omg.CORBA.portable.Delegate delegate = ((org.omg.CORBA.portable.ObjectImpl)obj)._get_delegate ();
      me.az1.dblab.Common._DatabaseControllerCorbaStub stub = new me.az1.dblab.Common._DatabaseControllerCorbaStub ();
      stub._set_delegate(delegate);
      return stub;
    }
  }

}