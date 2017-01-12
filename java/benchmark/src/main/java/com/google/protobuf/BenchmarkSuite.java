package com.google.protobuf;

import com.google.protobuf.test.UnittestImportProto3.ImportEnum;
import com.google.protobuf.test.UnittestImportProto3.ImportMessage;
import com.google.protobuf.test.UnittestImportPublicProto3.PublicImportMessage;
import java.io.ByteArrayOutputStream;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.State;
import protobuf_unittest.UnittestProto.ForeignEnum;
import protobuf_unittest.UnittestProto.ForeignMessage;
import protobuf_unittest.UnittestProto.TestAllTypes;

@State(Scope.Benchmark)
public class BenchmarkSuite {
  private static TestAllTypes proto3TestAllTypes =
      TestDataGenerator.getProto3TestAllTypes();

  private static TestAllTypes largeTestAllTypes =
      TestDataGenerator.getLargeTestAllTypes();

  static {
    System.out.println("TestAllTypes: " + proto3TestAllTypes.getSerializedSize());
    System.out.println("large TestAllTypes: " + largeTestAllTypes.getSerializedSize());
  }

  private static long writeByteArray(Message prototype) throws Exception {
    return prototype.toByteArray().length;
  }

  private static long writeByteString(Message prototype) throws Exception {
    return prototype.toByteString().size();
  }

  private static long writeByteArrayStream(Message prototype) throws Exception {
    ByteArrayOutputStream output = new ByteArrayOutputStream(
        Math.min(prototype.getSerializedSize(),
            CodedOutputStream.DEFAULT_BUFFER_SIZE));
    prototype.writeTo(output);
    return output.size();
  }

  @Benchmark
  public long proto3TestAllTypesWriteByteString() throws Exception {
    return writeByteString(proto3TestAllTypes);
  }

  @Benchmark
  public long proto3TestAllTypesWriteByteArray() throws Exception {
    return writeByteArray(proto3TestAllTypes);
  }

  @Benchmark
  public long proto3TestAllTypesWriteByteArrayStream() throws Exception {
    return writeByteArrayStream(proto3TestAllTypes);
  }

  @Benchmark
  public long largeTestAllTypesWriteByteString() throws Exception {
    return writeByteString(largeTestAllTypes);
  }

  @Benchmark
  public long largeTestAllTypesWriteByteArray() throws Exception {
    return writeByteArray(largeTestAllTypes);
  }

  @Benchmark
  public long largeTestAllTypesWriteByteArrayStream() throws Exception {
    return writeByteArrayStream(largeTestAllTypes);
  }
}

class TestDataGenerator {
  static TestAllTypes getProto3TestAllTypes() {
    TestAllTypes.Builder builder = TestAllTypes.newBuilder();
    setAllFields(builder);
    return builder.build();
  }

  static TestAllTypes getLargeTestAllTypes() {
    TestAllTypes.Builder builder = TestAllTypes.newBuilder();
    for (int i = 0; i < 100; ++i) {
      builder.addChildren(getProto3TestAllTypes());
    }
    return builder.build();
  }

  private static ByteString toBytes(String str) {
    try {
      return ByteString.copyFrom(str.getBytes("UTF-8"));
    } catch (Exception e) {
      throw new RuntimeException("unexpected exception: " + e.getMessage());
    }
  }

  private static void setAllFields(TestAllTypes.Builder message) {
    message.setSingleInt32   (101);
    message.setSingleInt64   (102);
    message.setSingleUint32  (103);
    message.setSingleUint64  (104);
    message.setSingleSint32  (105);
    message.setSingleSint64  (106);
    message.setSingleFixed32 (107);
    message.setSingleFixed64 (108);
    message.setSingleSfixed32(109);
    message.setSingleSfixed64(110);
    message.setSingleFloat   (111);
    message.setSingleDouble  (112);
    message.setSingleBool    (true);
    message.setSingleString  ("115");
    message.setSingleBytes   (toBytes("116"));

    message.setSingleNestedMessage(
      TestAllTypes.NestedMessage.newBuilder().setBb(118).build());
    message.setSingleForeignMessage(
      ForeignMessage.newBuilder().setC(119).build());
    message.setSingleImportMessage(
      ImportMessage.newBuilder().setD(120).build());
    message.setSinglePublicImportMessage(
      PublicImportMessage.newBuilder().setE(126).build());

    message.setSingleNestedEnum(TestAllTypes.NestedEnum.BAZ);
    message.setSingleForeignEnum(ForeignEnum.FOREIGN_BAZ);
    message.setSingleImportEnum(ImportEnum.IMPORT_BAZ);

    // -----------------------------------------------------------------

    message.addRepeatedInt32   (201);
    message.addRepeatedInt64   (202);
    message.addRepeatedUint32  (203);
    message.addRepeatedUint64  (204);
    message.addRepeatedSint32  (205);
    message.addRepeatedSint64  (206);
    message.addRepeatedFixed32 (207);
    message.addRepeatedFixed64 (208);
    message.addRepeatedSfixed32(209);
    message.addRepeatedSfixed64(210);
    message.addRepeatedFloat   (211);
    message.addRepeatedDouble  (212);
    message.addRepeatedBool    (true);
    message.addRepeatedString  ("215");
    message.addRepeatedBytes   (toBytes("216"));

    message.addRepeatedNestedMessage(
      TestAllTypes.NestedMessage.newBuilder().setBb(218).build());
    message.addRepeatedForeignMessage(
      ForeignMessage.newBuilder().setC(219).build());
    message.addRepeatedImportMessage(
      ImportMessage.newBuilder().setD(220).build());

    message.addRepeatedNestedEnum(TestAllTypes.NestedEnum.BAR);
    message.addRepeatedForeignEnum(ForeignEnum.FOREIGN_BAR);
    message.addRepeatedImportEnum(ImportEnum.IMPORT_BAR);

    // Add a second one of each field.
    message.addRepeatedInt32   (301);
    message.addRepeatedInt64   (302);
    message.addRepeatedUint32  (303);
    message.addRepeatedUint64  (304);
    message.addRepeatedSint32  (305);
    message.addRepeatedSint64  (306);
    message.addRepeatedFixed32 (307);
    message.addRepeatedFixed64 (308);
    message.addRepeatedSfixed32(309);
    message.addRepeatedSfixed64(310);
    message.addRepeatedFloat   (311);
    message.addRepeatedDouble  (312);
    message.addRepeatedBool    (false);
    message.addRepeatedString  ("315");
    message.addRepeatedBytes   (toBytes("316"));

    message.addRepeatedNestedMessage(
      TestAllTypes.NestedMessage.newBuilder().setBb(318).build());
    message.addRepeatedForeignMessage(
      ForeignMessage.newBuilder().setC(319).build());
    message.addRepeatedImportMessage(
      ImportMessage.newBuilder().setD(320).build());

    message.addRepeatedNestedEnum(TestAllTypes.NestedEnum.BAZ);
    message.addRepeatedForeignEnum(ForeignEnum.FOREIGN_BAZ);
    message.addRepeatedImportEnum(ImportEnum.IMPORT_BAZ);
  }
}
