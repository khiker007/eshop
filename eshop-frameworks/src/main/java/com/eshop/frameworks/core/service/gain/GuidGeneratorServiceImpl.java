package com.eshop.frameworks.core.service.gain;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.SecureRandom;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.eshop.common.util.date.DateUtils;
import com.eshop.frameworks.core.dao.common.GuidDao;
import com.eshop.frameworks.core.entity.GuidItem;

/**
 * 
 * @description
 * @author : spencer
 */
@Service("guidGeneratorService")
public class GuidGeneratorServiceImpl implements GuidGeneratorService {

    private static String localAddress;
    private static String spit = "";
    private static Map<String, Long> map = new HashMap<String, Long>();
    private static long maxAutoId;
    private static String randomString;
    private static boolean initFlag = false;

    @Autowired
    private GuidDao guidDao;

    public GuidGeneratorServiceImpl() {
        init();
    }

    public String getGuid(String type) {
        String id = "";
        if (type == null || type.trim().length() == 0)
            type = "NULL";
        else if (type.length() > 4)
            type = type.substring(0, 4);
        id += type;
        id += spit;
        id += getLocalAddress(true);
        id += spit;
        id += getNowTime();
        id += spit;
        id += getAutoId(type);
        id += spit;
        id += getRandomString(2);

        return id;
    }

    public String getGuid() {
        return getGuid("GUID");
    }

    private synchronized void init() {
        if (initFlag == true)
            return;
        localAddress = getLocalAddress(true);
        initFlag = true;
        maxAutoId = this.getMaxNumber(8);
    }

    private String getNowTime() {
        DateFormat sf = new SimpleDateFormat("yyyyMMddHHmmss");
        return sf.format(new Date());
    }

    private synchronized String getAutoId(String type) {
        if (map.containsKey(type)) {
            Long no = map.get(type);
            String nos = getFixString(no.intValue(), 8);
            Long ti = no + 1;
            if (ti > maxAutoId)
                ti = 1L;
            map.put(type, ti);
            return nos;
        } else {
            String nos = getFixString(1, 8);
            map.put(type, 2L);
            return nos;
        }
    }

    private long getMaxNumber(int len) {
        StringBuffer sb = new StringBuffer(len);
        for (int i = 0; i < len; i++) {
            sb.append("9");
        }
        return Long.parseLong(sb.toString());
    }

    private String getRandomString(int len) {
        if (randomString != null)
            return randomString;

        byte[] bs = new byte[len];
        Random rand = new SecureRandom();
        for (int i = 0; i < len; i++) {
            byte b = (byte) (65 + rand.nextInt(26));
            bs[i] = b;
        }
        randomString = new String(bs);
        return randomString;
    }

    /**
     * 长度补冲，前面加0
     * 
     * @param num
     * @param len
     * @return String
     */
    private String getFixString(long num, int len) {

        String tp = "" + num;
        if (len == 0) {
            return tp;
        }
        if (tp.length() == len)
            return tp;
        if (tp.length() > len)
            return tp.substring(0, len);
        for (int i = 0; i <= len / 4 + 1; i++) {
            tp = "00000" + tp;
        }
        return tp.substring(tp.length() - len);
    }

    public String getLocalAddress() {
        return getLocalAddress(false);
    }

    private String getLocalAddress(boolean onlyNumber) {
        if (localAddress != null)
            return localAddress;
        String ip = "";
        try {
            InetAddress ad = InetAddress.getLocalHost();
            ip = ad.getHostAddress();
        } catch (UnknownHostException e) {
            e.printStackTrace();
            if (onlyNumber)
                ip = "255255255255";
            else
                ip = "255.255.255.255";
        }
        if (!onlyNumber)
            return ip;
        StringBuffer sb = new StringBuffer(30);
        int begin = 0, size = ip.length();
        while (begin < size) {
            int t = ip.indexOf(".", begin);
            if (t < 0)
                t = size;
            String p = "000" + ip.substring(begin, t);
            sb.append(p.substring(p.length() - 3));
            begin = t + 1;
        }
        localAddress = sb.toString();
        try {
            if (localAddress.length() > 12)
                localAddress = localAddress.substring(localAddress.length() - 12);
        } catch (Exception e) {
            e.printStackTrace();
            localAddress = "255255255255";
        }
        return localAddress;
    }

    public synchronized String getSpecialId(final String project, int length) {
        String iSpecId;
        List<GuidItem> guidItemlist = guidDao.getGuidItemByProject(project);
        if (guidItemlist == null || guidItemlist.size() == 0) {
            // 若根据业务查询为空，则写入一条
            GuidItem guidItemAdd = new GuidItem();
            guidItemAdd.setProject(project);
            guidDao.addGuidItem(guidItemAdd);
            iSpecId = this.getFixString(GuidItem.getDefaultAutoId(), length);
        } else {
            GuidItem guidItemUpdate = guidItemlist.get(0);
            // 获取现在的ID
            long naid = guidItemUpdate.getAutoId();
            // 更新加1
            guidDao.updateGuidItem(guidItemUpdate);
            iSpecId = this.getFixString(naid, length);
        }
        return iSpecId;
    }

    /**
     * 
     * 根据关键字和长度生成
     */
    public String gainCode(String prefix) {
        // 默认生成，不补长度传参数0
        String no = getSpecialId(prefix, 0);
        return no;
    }

    /**
     * 根据关键字和长度生成
     */
    @Override
    public String gainCode(String prefix, int length) {
        String no = getSpecialId(prefix, length);
        return no;
    }

    /**
     * 获得群组的ID
     * 
     * @param prefix
     *            前缀
     * @param length
     *            最小默认长度，不足时前面补充0
     * @return
     */
    @Override
    public String gainDisCode(String prefix, int length) {
        String node = gainCode(prefix, length);
        StringBuffer buffer = new StringBuffer();
        // 返回关键字加时间+唯一值
        buffer.append(prefix).append(DateUtils.formatDate(new Date(), "yyyyMMdd"));
        buffer.append(node);
        return buffer.toString();
    }
}
