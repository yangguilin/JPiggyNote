package com.ygl.piggynote.service.impl;

import com.ygl.piggynote.bean.go.ArticleBean;
import com.ygl.piggynote.bean.go.MonsterBean;
import com.ygl.piggynote.service.GoService;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by yanggavin on 15/9/1.
 */
@Service("goService")
public class GoServiceImpl implements GoService {
    @Override
    public List<MonsterBean> loadMonsterData() {
        List<MonsterBean> list = new ArrayList<MonsterBean>();
        try
        {
            ClassLoader classLoader = getClass().getClassLoader();
            File file = new File(classLoader.getResource("go/monster.xml").getFile());
            SAXReader reader = new SAXReader();
            reader.setEncoding("UTF-8");
            Document doc = reader.read(file);
            Element root = doc.getRootElement();
            Element foo;
            for (Iterator i = root.elementIterator("item"); i.hasNext();){
                foo = (Element)i.next();
                MonsterBean mb = new MonsterBean();
                mb.setId(foo.elementText("id"));
                mb.setName(foo.elementText("name"));
                mb.setLife(Integer.parseInt(foo.elementText("life")));
                mb.setAttack(Integer.parseInt(foo.elementText("attack")));
                mb.setDefend(Integer.parseInt(foo.elementText("defend")));
                mb.setSpeed(Integer.parseInt(foo.elementText("speed")));
                mb.setGrowFactor(Integer.parseInt(foo.elementText("growFactor")));
                list.add(mb);
            }
        }catch (Exception e){

        }
        return list;
    }

    @Override
    public List<ArticleBean> loadArticleData() {
        return null;
    }
}
