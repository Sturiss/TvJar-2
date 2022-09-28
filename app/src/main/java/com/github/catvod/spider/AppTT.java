package com.github.catvod.spider;

import android.content.Context;
import android.os.Build;
import android.os.Build.VERSION;
import android.text.TextUtils;
import android.util.Base64;
import com.github.catvod.crawler.Spider;
import com.github.catvod.spider.merge.Vf;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import org.json.JSONArray;
import org.json.JSONObject;

public class AppTT extends Spider {
    private String R;

    public String detailContent(List<String> list) {
        String str = "\\$";
        String str2 = "player_info";
        String str3 = "$$$";
        String str4 = "vod_content";
        String str5 = "vod_director";
        String str6 = "vod_actor";
        String str7 = "vod_remarks";
        String str8 = "vod_area";
        String str9 = "vod_year";
        String str10 = "vod_pic";
        String str11 = "vod_id";
        String str12 = "vod_name";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://45.207.11.9:7766/api.php/v1.vod/detilldata?vod_id=");
            stringBuilder.append((String) list.get(0));
            String stringBuilder2 = stringBuilder.toString();
            JSONObject jSONObject = new JSONObject(R(Vf.h(stringBuilder2, tV(stringBuilder2)))).getJSONObject("data");
            JSONObject jSONObject2 = new JSONObject();
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject3 = jSONObject.getJSONObject("videoInfo");
            JSONObject jSONObject4 = jSONObject2;
            jSONObject2 = new JSONObject();
            jSONObject3.getString(str12);
            JSONArray jSONArray2 = jSONArray;
            jSONObject2.put(str11, jSONObject3.getString(str11));
            jSONObject2.put(str12, jSONObject3.getString(str12));
            jSONObject2.put(str10, jSONObject3.getString(str10));
            jSONObject2.put(str9, jSONObject3.getString(str9));
            jSONObject2.put(str8, jSONObject3.getString(str8));
            jSONObject2.put(str7, jSONObject3.getString(str7));
            jSONObject2.put(str6, jSONObject3.getString(str6));
            jSONObject2.put(str5, jSONObject3.getString(str5));
            jSONObject2.put(str4, jSONObject3.getString(str4).trim());
            JSONArray jSONArray3 = jSONObject.getJSONArray("source");
            LinkedHashMap linkedHashMap = new LinkedHashMap();
            int i = 0;
            while (true) {
                str7 = "list";
                if (i >= jSONArray3.length()) {
                    break;
                }
                JSONArray jSONArray4 = jSONArray3.getJSONObject(i).getJSONArray(str7);
                str7 = jSONArray3.getJSONObject(i).getJSONObject(str2).getString("show");
                str9 = jSONArray3.getJSONObject(i).getJSONObject(str2).getString("parse");
                ArrayList arrayList = (ArrayList) linkedHashMap.get(str7);
                if (arrayList == null) {
                    arrayList = new ArrayList();
                    linkedHashMap.put(str7, arrayList);
                }
                for (int i2 = 0; i2 < jSONArray4.length(); i2++) {
                    if (!jSONArray4.get(i2).toString().contains(".m3u8")) {
                        if (!jSONArray4.get(i2).toString().contains(".mp4")) {
                            str11 = jSONArray4.get(i2).toString().split(str)[0];
                            stringBuilder2 = jSONArray4.get(i2).toString().split(str)[1];
                            StringBuilder stringBuilder3 = new StringBuilder();
                            stringBuilder3.append(str11);
                            stringBuilder3.append("$");
                            stringBuilder3.append(str9);
                            stringBuilder3.append(stringBuilder2);
                            arrayList.add(stringBuilder3.toString());
                        }
                    }
                    arrayList.add(jSONArray4.get(i2).toString());
                }
                i++;
            }
            String join = TextUtils.join(str3, linkedHashMap.keySet());
            StringBuilder stringBuilder4 = new StringBuilder();
            int size = (short) linkedHashMap.size();
            for (ArrayList join2 : linkedHashMap.values()) {
                size = (short) (size - 1);
                stringBuilder4.append(TextUtils.join("#", join2));
                if (size > 0) {
                    stringBuilder4.append(str3);
                }
            }
            jSONObject2.put("vod_play_from", join);
            jSONObject2.put("vod_play_url", stringBuilder4.toString());
            jSONArray3 = jSONArray2;
            jSONArray3.put(jSONObject2);
            JSONObject jSONObject5 = jSONObject4;
            jSONObject5.put(str7, jSONArray3);
            return jSONObject5.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String categoryContent(String str, String str2, boolean z, HashMap<String, String> hashMap) {
        String str3 = "vod_remarks";
        String str4 = "vod_pic";
        String str5 = "vod_name";
        String str6 = "total";
        String str7 = "vod_id";
        String str8 = "page";
        String str9 = "list";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://45.207.11.9:7766/api.php/v1.vod?type=");
            stringBuilder.append(str);
            stringBuilder.append("&page=");
            stringBuilder.append(str2);
            stringBuilder.append("&pagesize=20");
            str = stringBuilder.toString();
            JSONObject jSONObject = new JSONObject(R(Vf.h(str, tV(str)))).getJSONObject("data");
            JSONArray jSONArray = jSONObject.getJSONArray(str9);
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject2 = jSONArray.getJSONObject(i);
                JSONObject jSONObject3 = new JSONObject();
                jSONObject3.put(str7, jSONObject2.getString(str7));
                jSONObject3.put(str5, jSONObject2.getString(str5));
                jSONObject3.put(str4, jSONObject2.getString(str4));
                jSONObject3.put(str3, jSONObject2.getString(str3));
                jSONArray2.put(jSONObject3);
            }
            JSONObject jSONObject4 = new JSONObject();
            int i2 = jSONObject.getInt(str8);
            int i3 = jSONObject.getInt(str6);
            jSONArray2.length();
            jSONObject4.put(str8, i2);
            jSONObject4.put("pagecount", i3 / 20);
            jSONObject4.put("limit", 20);
            jSONObject4.put(str6, i3);
            jSONObject4.put(str9, jSONArray2);
            return jSONObject4.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String homeContent(boolean z) {
        String str = "type_name";
        String str2 = "type_id";
        try {
            JSONArray jSONArray = new JSONArray();
            JSONObject jSONObject = new JSONObject();
            jSONObject.put(str2, 1);
            jSONObject.put(str, "电影");
            jSONArray.put(jSONObject);
            jSONObject = new JSONObject();
            jSONObject.put(str2, 2);
            jSONObject.put(str, "连续剧");
            jSONArray.put(jSONObject);
            jSONObject = new JSONObject();
            jSONObject.put(str2, 3);
            jSONObject.put(str, "动漫");
            jSONArray.put(jSONObject);
            jSONObject = new JSONObject();
            jSONObject.put(str2, 4);
            jSONObject.put(str, "综艺");
            jSONArray.put(jSONObject);
            JSONObject jSONObject2 = new JSONObject();
            jSONObject2.put("class", jSONArray);
            return jSONObject2.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String searchContent(String str, boolean z) {
        String str2 = "vod_remarks";
        String str3 = "vod_pic";
        String str4 = "vod_name";
        String str5 = "vod_id";
        String str6 = "list";
        try {
            StringBuilder stringBuilder = new StringBuilder();
            stringBuilder.append("http://45.207.11.9:7766/api.php/v1.vod?wd=");
            stringBuilder.append(URLEncoder.encode(str));
            str = stringBuilder.toString();
            JSONArray jSONArray = new JSONObject(R(Vf.h(str, tV(str)))).getJSONObject("data").getJSONArray(str6);
            JSONArray jSONArray2 = new JSONArray();
            for (int i = 0; i < jSONArray.length(); i++) {
                JSONObject jSONObject = jSONArray.getJSONObject(i);
                JSONObject jSONObject2 = new JSONObject();
                jSONObject2.put(str5, jSONObject.getString(str5));
                jSONObject2.put(str4, jSONObject.getString(str4));
                jSONObject2.put(str3, jSONObject.getString(str3));
                jSONObject2.put(str2, jSONObject.getString(str2));
                jSONArray2.put(jSONObject2);
            }
            JSONObject jSONObject3 = new JSONObject();
            jSONObject3.put(str6, jSONArray2);
            return jSONObject3.toString();
        } catch (Throwable unused) {
            return "";
        }
    }

    public String playerContent(String str, String str2, List<String> list) {
        str = "";
        try {
            Object str22;
            String str3 = "url";
            if (!str22.contains(".m3u8")) {
                if (!str22.contains(".mp4")) {
                    StringBuilder stringBuilder = new StringBuilder();
                    stringBuilder.append(str22);
                    stringBuilder.append("&sc=1&token=");
                    stringBuilder.append(getToken());
                    str22 = stringBuilder.toString();
                    str22 = new JSONObject(decrypt(Vf.h(str22, tV(str22)), "UTF-8", "SRK#e%4UYtU#KiEo*vsPqpr0cC4bxAQW", "o6sYmm*x5hn#rcCt")).getString(str3);
                }
            }
            JSONObject jSONObject = new JSONObject();
            jSONObject.put("parse", 0);
            jSONObject.put("playUrl", str);
            jSONObject.put(str3, str22);
            return jSONObject.toString();
        } catch (Throwable unused) {
            return str;
        }
    }

    private String zh(String str) {
        if (str.contains("api.php/app") || str.contains("xgapp") || str.contains("freekan")) {
            return "Dart/2.14 (dart:io)";
        }
        if (str.contains("zsb") || str.contains("fkxs") || str.contains("xays") || str.contains("xcys") || str.contains("szys") || str.contains("dxys") || str.contains("ytys") || str.contains("qnys")) {
            return "Dart/2.15 (dart:io)";
        }
        return str.contains(".vod") ? "okhttp/4.1.0" : "Dalvik/2.1.0";
    }

    public String decrypt(String str, String str2, String str3, String str4) {
        try {
            SecretKeySpec secretKeySpec = new SecretKeySpec(str3.getBytes("UTF-8"), "AES");
            Cipher instance = Cipher.getInstance("AES/CTR/PKCS5Padding");
            instance.init(2, secretKeySpec, new IvParameterSpec(str4.getBytes()));
            return new String(instance.doFinal(Base64.decode(str, 0)), str2);
        } catch (Exception unused) {
            return null;
        }
    }

    public String encrypt(String str, String str2, String str3, String str4) {
        try {
            Cipher instance = Cipher.getInstance("AES/CTR/PKCS5Padding");
            instance.init(1, new SecretKeySpec(str3.getBytes(), "AES"), new IvParameterSpec(str4.getBytes()));
            return Base64.encodeToString(instance.doFinal(str.getBytes(str2)), 0);
        } catch (Exception unused) {
            return null;
        }
    }

    public AppTT() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Dalvik/2.1.0 (Linux; U; Android ");
        stringBuilder.append(VERSION.RELEASE);
        stringBuilder.append("; ");
        stringBuilder.append(Build.MODEL);
        stringBuilder.append(" Build/");
        stringBuilder.append(Build.ID);
        stringBuilder.append(")");
        this.R = stringBuilder.toString();
    }

    private HashMap<String, String> tV(String str) {
        HashMap hashMap = new HashMap();
        hashMap.put("User-Agent", zh(str));
        return hashMap;
    }

    protected String R(String str) {
        return str;
    }

    public String getToken() {
        return encrypt(Long.valueOf(new Date().getTime()).toString(), "UTF-8", "SRK#e%4UYtU#KiEo*vsPqpr0cC4bxAQW", "o6sYmm*x5hn#rcCt");
    }

    /*  JADX ERROR: NullPointerException in pass: BlockSplitter
        java.lang.NullPointerException: Attempt to invoke virtual method 'boolean jadx.core.dex.attributes.AttrNode.contains(jadx.core.dex.attributes.AType)' on a null object reference
        	at jadx.core.dex.visitors.blocksmaker.BlockSplitter.connectExceptionHandlers(Unknown Source)
        	at jadx.core.dex.visitors.blocksmaker.BlockSplitter.setupConnections(Unknown Source)
        	at jadx.core.dex.visitors.blocksmaker.BlockSplitter.splitBasicBlocks(Unknown Source)
        	at jadx.core.dex.visitors.blocksmaker.BlockSplitter.visit(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(Unknown Source)
        	at jadx.core.dex.visitors.DepthTraversal.visit(Unknown Source)
        	at jadx.core.ProcessClass.process(Unknown Source)
        	at jadx.api.JadxDecompiler.processClass(Unknown Source)
        	at jadx.api.JavaClass.decompile(Unknown Source)
        */
    public java.lang.String homeVideoContent() {
        /*
        r10 = this;
        r0 = "vod_remarks";
        r1 = "vod_pic";
        r2 = "vod_name";
        r3 = "vod_id";
        r4 = new org.json.JSONArray;	 Catch:{ all -> 0x006d }
        r4.<init>();	 Catch:{ all -> 0x006d }
        r5 = "http://45.207.11.9:7766/api.php/v1.vod/curnavitemlist?type_id=0";	 Catch:{ Exception -> 0x005e }
        r6 = r10.tV(r5);	 Catch:{ Exception -> 0x005e }
        r5 = com.github.catvod.spider.merge.Vf.h(r5, r6);	 Catch:{ Exception -> 0x005e }
        r6 = new org.json.JSONObject;	 Catch:{ Exception -> 0x005e }
        r5 = r10.R(r5);	 Catch:{ Exception -> 0x005e }
        r6.<init>(r5);	 Catch:{ Exception -> 0x005e }
        r5 = "data";	 Catch:{ Exception -> 0x005e }
        r5 = r6.getJSONObject(r5);	 Catch:{ Exception -> 0x005e }
        r6 = "swiperList";	 Catch:{ Exception -> 0x005e }
        r5 = r5.getJSONArray(r6);	 Catch:{ Exception -> 0x005e }
        r6 = 0;	 Catch:{ Exception -> 0x005e }
    L_0x002d:
        r7 = r5.length();	 Catch:{ Exception -> 0x005e }
        if (r6 >= r7) goto L_0x005e;	 Catch:{ Exception -> 0x005e }
    L_0x0033:
        r7 = r5.getJSONObject(r6);	 Catch:{ Exception -> 0x005e }
        r8 = new org.json.JSONObject;	 Catch:{ Exception -> 0x005e }
        r8.<init>();	 Catch:{ Exception -> 0x005e }
        r9 = r7.getString(r3);	 Catch:{ Exception -> 0x005e }
        r8.put(r3, r9);	 Catch:{ Exception -> 0x005e }
        r9 = r7.getString(r2);	 Catch:{ Exception -> 0x005e }
        r8.put(r2, r9);	 Catch:{ Exception -> 0x005e }
        r9 = r7.getString(r1);	 Catch:{ Exception -> 0x005e }
        r8.put(r1, r9);	 Catch:{ Exception -> 0x005e }
        r7 = r7.getString(r0);	 Catch:{ Exception -> 0x005e }
        r8.put(r0, r7);	 Catch:{ Exception -> 0x005e }
        r4.put(r8);	 Catch:{ Exception -> 0x005e }
        r6 = r6 + 1;
        goto L_0x002d;
    L_0x005e:
        r0 = new org.json.JSONObject;
        r0.<init>();
        r1 = "list";
        r0.put(r1, r4);
        r0 = r0.toString();
        return r0;
        r0 = "";
        return r0;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.github.catvod.spider.AppTT.homeVideoContent():java.lang.String");
    }

    public void init(Context context) {
        super.init(context);
    }
}
