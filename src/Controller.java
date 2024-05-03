import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.File;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;

public class Controller implements SISystem {
    private final Document document;
    private final Element root;
    private final File f;

    private int pStuNum;
    private int uStuNum;
    public static final int MAX_STU_NUM = 100;

    Controller(File file) {
        try {
            f=file;
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            document = db.parse(f);
            root = document.getDocumentElement();
            pStuNum=getPStudent()==null?0:getPStudent().length;
            uStuNum=getUStudent()==null?0:getUStudent().length;
        } catch (ParserConfigurationException | IOException | SAXException e) {
            throw new RuntimeException(e);
        }
    }

    public void save() {
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        try {
            Transformer transformer = transformerFactory.newTransformer();
            transformer.setOutputProperty("encoding", "utf-8");
            StringWriter stringWriter = new StringWriter();
            transformer.transform(new DOMSource(document), new StreamResult(stringWriter));
            System.out.println(stringWriter);
            transformer.transform(new DOMSource(document), new StreamResult(f));
        } catch (TransformerException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void addStudent(UStudent u) {
        Element studentElement = document.createElement("student");
        studentElement.setAttribute("name", u.getName());
        studentElement.setAttribute("age", String.valueOf(u.getAge()));
        studentElement.setAttribute("major", u.getMajor());
        studentElement.setAttribute("street", u.getAddress().getStreet());
        studentElement.setAttribute("city", u.getAddress().getCity());
        studentElement.setAttribute("state", u.getAddress().getState());
        studentElement.setAttribute("zip", u.getAddress().getZip());
        studentElement.setAttribute("id", String.valueOf(u.getId()));
        studentElement.setAttribute("cls", u.getCls());

        Element gradesElement = document.createElement("grades");
        u.getGrades().forEach((key, value)->{
            Element subject = document.createElement("subject");
            Element grade = document.createElement("grade");
            Element gradeName = document.createElement("gradeName");
            gradeName.setTextContent(key);
            grade.setTextContent(String.valueOf(value));
            subject.appendChild(gradeName);
            subject.appendChild(grade);
            gradesElement.appendChild(subject);
        });
        studentElement.appendChild(gradesElement);
        if(root.getElementsByTagName("UStudent").item(0)==null) {
            Element USelement = document.createElement("UStudent");
            USelement.appendChild(studentElement);
            root.appendChild(USelement);
        } else {
            root.getElementsByTagName("UStudent").item(0).appendChild(studentElement);
        }

        uStuNum=getUStudent().length;
    }

    @Override
    public void addStudent(PStudent p) {
        Element studentElement = document.createElement("student");
        studentElement.setAttribute("name", p.getName());
        studentElement.setAttribute("age", String.valueOf(p.getAge()));
        studentElement.setAttribute("street", p.getAddress().getStreet());
        studentElement.setAttribute("city", p.getAddress().getCity());
        studentElement.setAttribute("state", p.getAddress().getState());
        studentElement.setAttribute("zip", p.getAddress().getZip());
        studentElement.setAttribute("id", String.valueOf(p.getId()));
        studentElement.setAttribute("cls", p.getCls());
        studentElement.setAttribute("research", p.getResearch());
        studentElement.setAttribute("tutor", p.getTutor());

        Element gradesElement = document.createElement("grades");
        p.getGrades().forEach((key, value)->{
            Element subject = document.createElement("subject");
            Element grade = document.createElement("grade");
            Element gradeName = document.createElement("gradeName");
            gradeName.setTextContent(key);
            grade.setTextContent(String.valueOf(value));
            subject.appendChild(gradeName);
            subject.appendChild(grade);
            gradesElement.appendChild(subject);
        });
        studentElement.appendChild(gradesElement);

        if(root.getElementsByTagName("PStudent").item(0)==null) {
            Element PSelement = document.createElement("PStudent");
            PSelement.appendChild(studentElement);
            root.appendChild(PSelement);
        } else {
            root.getElementsByTagName("PStudent").item(0).appendChild(studentElement);
        }

        pStuNum=getPStudent().length;
    }

    @Override
    public void removeUStudent(int id) {
        Node ustudents = root.getElementsByTagName("UStudent").item(0);
        for(int i=0; i<ustudents.getChildNodes().getLength(); i++) {
            if(ustudents.getChildNodes().item(i).getAttributes().getNamedItem("id").getTextContent().equals(String.valueOf(id))){
                ustudents.removeChild(ustudents.getChildNodes().item(i));
                i--;
            }
        }

        uStuNum=getUStudent().length;
    }

    @Override
    public void removePStudent(int id) {
        Node pstudents = root.getElementsByTagName("PStudent").item(0);
        for(int i = 0; i< pstudents.getChildNodes().getLength(); i++) {
            String idddd = pstudents.getChildNodes().item(i).getAttributes().getNamedItem("id").getTextContent();
            if(pstudents.getChildNodes().item(i).getAttributes().getNamedItem("id").getTextContent().equals(String.valueOf(id))){
                pstudents.removeChild(pstudents.getChildNodes().item(i));
                i--;
            }
        }

        pStuNum=getPStudent().length;
    }

    @Override
    public void putStudent(int id, UStudent u) {
        this.removeUStudent(id);
        this.addStudent(u);
    }

    @Override
    public void putStudent(int id, PStudent p) {
        this.removePStudent(id);
        this.addStudent(p);
    }

    @Override
    public UStudent[] getUStudentByID(int id) {
        Node ustudents = root.getElementsByTagName("UStudent").item(0);
        ArrayList<UStudent> students = new ArrayList<>();
        if(ustudents==null) {
            return null;
        }

        for(int i=0; i<ustudents.getChildNodes().getLength(); i++) {
            if(ustudents.getChildNodes().item(i).getAttributes().getNamedItem("id").getTextContent().equals(String.valueOf(id))) {
                students.add(nodeToUStudent(ustudents.getChildNodes().item(i)));
            }
        }
        return students.toArray(new UStudent[0]);
    }

    @Override
    public UStudent[] getUStudentByName(String str) {
        Node ustudents = root.getElementsByTagName("UStudent").item(0);
        ArrayList<UStudent> students = new ArrayList<>();
        if(ustudents==null) {
            return null;
        }

        for(int i=0; i<ustudents.getChildNodes().getLength(); i++) {
            if(ustudents.getChildNodes().item(i).getAttributes().getNamedItem("name").getTextContent().equals(str)) {
                students.add(nodeToUStudent(ustudents.getChildNodes().item(i)));
            }
        }
        return students.toArray(new UStudent[0]);
    }

    @Override
    public UStudent[] getUStudentByCLS(String str) {
        Node ustudents = root.getElementsByTagName("UStudent").item(0);
        ArrayList<UStudent> students = new ArrayList<>();
        if(ustudents==null) {
            return null;
        }

        for(int i=0; i<ustudents.getChildNodes().getLength(); i++) {
            if(ustudents.getChildNodes().item(i).getAttributes().getNamedItem("cls").getTextContent().equals(str)) {
                students.add(nodeToUStudent(ustudents.getChildNodes().item(i)));
            }
        }
        return students.toArray(new UStudent[0]);
    }

    @Override
    public PStudent[] getPStudentByID(int id) {
        Node pstudents = root.getElementsByTagName("PStudent").item(0);
        ArrayList<PStudent> students = new ArrayList<>();
        if(pstudents==null) {
            return null;
        }

        for(int i = 0; i< pstudents.getChildNodes().getLength(); i++) {
            if(pstudents.getChildNodes().item(i).getAttributes().getNamedItem("id").getTextContent().equals(String.valueOf(id))) {
                students.add(nodeToPStudent(pstudents.getChildNodes().item(i)));
            }
        }
        return students.toArray(new PStudent[0]);
    }

    @Override
    public PStudent[] getPStudentByName(String str) {
        Node pstudents = root.getElementsByTagName("PStudent").item(0);
        ArrayList<PStudent> students = new ArrayList<>();
        if(pstudents==null) {
            return null;
        }

        for(int i = 0; i< pstudents.getChildNodes().getLength(); i++) {
            if(pstudents.getChildNodes().item(i).getAttributes().getNamedItem("name").getTextContent().equals(str)) {
                students.add(nodeToPStudent(pstudents.getChildNodes().item(i)));
            }
        }
        return students.toArray(new PStudent[0]);
    }

    @Override
    public PStudent[] getPStudentByCLS(String str) {
        Node pstudents = root.getElementsByTagName("PStudent").item(0);
        ArrayList<PStudent> students = new ArrayList<>();
        if(pstudents==null) {
            return null;
        }

        for(int i = 0; i< pstudents.getChildNodes().getLength(); i++) {
            if(pstudents.getChildNodes().item(i).getAttributes().getNamedItem("cls").getTextContent().equals(str)) {
                students.add(nodeToPStudent(pstudents.getChildNodes().item(i)));
            }
        }
        return students.toArray(new PStudent[0]);
    }

    @Override
    public UStudent[] getUStudent() {
        Node ustudents = root.getElementsByTagName("UStudent").item(0);
        ArrayList<UStudent> students = new ArrayList<>();
        if(ustudents==null) {
            return null;
        }

        for(int i=0; i<ustudents.getChildNodes().getLength(); i++) {
                students.add(nodeToUStudent(ustudents.getChildNodes().item(i)));
        }
        return students.toArray(new UStudent[0]);
    }

    @Override
    public PStudent[] getPStudent() {
        Node pstudents = root.getElementsByTagName("PStudent").item(0);
        ArrayList<PStudent> students = new ArrayList<>();
        if(pstudents==null) {
            return null;
        }

        for(int i = 0; i< pstudents.getChildNodes().getLength(); i++) {
            students.add(nodeToPStudent(pstudents.getChildNodes().item(i)));
        }
        return students.toArray(new PStudent[0]);
    }

    private UStudent nodeToUStudent(Node node) {
        Node gradesNode = node.getChildNodes().item(0);
        HashMap<String, Integer> grades = new HashMap<>();

        for(int i=0; i<gradesNode.getChildNodes().getLength(); i++) {
            Element gradeElement = (Element) gradesNode.getChildNodes().item(i);
            grades.put(gradeElement.getChildNodes().item(0).getTextContent(), Integer.valueOf(gradeElement.getChildNodes().item(1).getTextContent()));
        }

        return new UStudent(
                node.getAttributes().getNamedItem("name").getTextContent(),
                Integer.parseInt(node.getAttributes().getNamedItem("age").getTextContent()),
                Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent()),
                node.getAttributes().getNamedItem("cls").getTextContent(),
                new Address(
                        node.getAttributes().getNamedItem("street").getTextContent(),
                        node.getAttributes().getNamedItem("city").getTextContent(),
                        node.getAttributes().getNamedItem("state").getTextContent(),
                        node.getAttributes().getNamedItem("zip").getTextContent()
                ),
                grades,
                node.getAttributes().getNamedItem("major").getTextContent()
        );
    }

    private PStudent nodeToPStudent(Node node) {
        Node gradesNode = node.getChildNodes().item(0);
        HashMap<String, Integer> grades = new HashMap<>();
        for(int i=0; i<gradesNode.getChildNodes().getLength(); i++) {
            Element gradeElement = (Element) gradesNode.getChildNodes().item(i);
            grades.put(gradeElement.getChildNodes().item(0).getTextContent(), Integer.valueOf(gradeElement.getChildNodes().item(1).getTextContent()));
        }

        return new PStudent(
                node.getAttributes().getNamedItem("name").getTextContent(),
                Integer.parseInt(node.getAttributes().getNamedItem("age").getTextContent()),
                Integer.parseInt(node.getAttributes().getNamedItem("id").getTextContent()),
                node.getAttributes().getNamedItem("cls").getTextContent(),
                new Address(
                        node.getAttributes().getNamedItem("street").getTextContent(),
                        node.getAttributes().getNamedItem("city").getTextContent(),
                        node.getAttributes().getNamedItem("state").getTextContent(),
                        node.getAttributes().getNamedItem("zip").getTextContent()
                ),
                grades,
                node.getAttributes().getNamedItem("research").getTextContent(),
                node.getAttributes().getNamedItem("tutor").getTextContent()
        );
    }

    public int getpStuNum() {
        return pStuNum;
    }

    public int getuStuNum() {
        return uStuNum;
    }
}
