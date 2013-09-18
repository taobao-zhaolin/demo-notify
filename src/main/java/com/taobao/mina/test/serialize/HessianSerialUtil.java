package com.taobao.mina.test.serialize;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

import com.caucho.hessian.io.HessianSerializerInput;
import com.caucho.hessian.io.HessianSerializerOutput;

public class HessianSerialUtil {
	/**

	 * 纯hessian序列化
	 * @param object
	 * @return
	 * @throws Exception
	 */

	public static byte[] serialize(Object object) throws Exception{

		if (object == null) {
			throw new NullPointerException();
		}

		ByteArrayOutputStream os = new ByteArrayOutputStream();
		HessianSerializerOutput hessianOutput = new HessianSerializerOutput(os);
		hessianOutput.writeObject(object);
		return os.toByteArray();

	}

	/**

	 * 纯hessian反序列化
	 * @param bytes
	 * @return
	 * @throws Exception
	 */

	public static Object deserialize(byte[] bytes) throws Exception{

		if(bytes==null){
		throw new NullPointerException();
		}
	
		ByteArrayInputStream is = new ByteArrayInputStream(bytes);
		HessianSerializerInput hessianInput=new HessianSerializerInput(is);
		Object object = hessianInput.readObject();
		return object;

	}
}
