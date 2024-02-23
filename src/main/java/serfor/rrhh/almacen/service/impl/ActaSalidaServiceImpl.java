package serfor.rrhh.almacen.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.AlmacenEntity;
import serfor.rrhh.almacen.entity.TransferenciaEntity;
import serfor.rrhh.almacen.service.ActaSalidaService;
import serfor.rrhh.almacen.service.AlmacenService;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class ActaSalidaServiceImpl implements ActaSalidaService {

    @Autowired
    private AlmacenService almacenService;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    SimpleDateFormat formatterHora = new SimpleDateFormat("HH:mm");
    Font titulo= new Font(Font.HELVETICA, 11f, Font.BOLD);
    Font subTitulo= new Font(Font.HELVETICA, 10f, Font.BOLD);
    static Font contenido= new Font(Font.HELVETICA, 8f, Font.COURIER);
    Font cabecera= new Font(Font.HELVETICA, 9f, Font.BOLD);
    Font subTituloTabla= new Font(Font.HELVETICA, 10f, Font.COURIER);
    Font subTitulo2= new Font(Font.HELVETICA, 10f, Font.COURIER);
    Font letraPeque = new Font(Font.HELVETICA, 6f, Font.COURIER);

    private XWPFDocument getDoc(String nameFile) throws NullPointerException, IOException {
        InputStream file = getClass().getClassLoader().getResourceAsStream(nameFile);
        return new XWPFDocument(file);
    }

    @Override
    public ByteArrayResource consolidadoActaSalida_PDF(List<TransferenciaEntity> transferencia) throws Exception {
        XWPFDocument doc = getDoc("formatoActa.docx");

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        doc.write(b);
        doc.close();

        /*
         * *********************************** USO DE LIBRERIA PDF
         *************************************/
        InputStream myInputStream = new ByteArrayInputStream(b.toByteArray());
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(myInputStream);
        File archivo = File.createTempFile("consolidadoActaSalida", ".pdf");

        FileOutputStream os = new FileOutputStream(archivo);

        createPdfActa(os, transferencia);
        os.flush();
        os.close();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] fileContent = Files.readAllBytes(archivo.toPath());
        return new ByteArrayResource(fileContent);

    }

    public void createPdfActa(FileOutputStream os, List<TransferenciaEntity> transferencia) {
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        document.setMargins(60, 60, 40, 40);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, os);
            Date dateNow = new Date();

            AlmacenEntity almacen = almacenService.getAlmacen(transferencia.get(0).getNuIdAlmacenOrigin());

            document.open();

            Paragraph titlePara1 = new Paragraph("ACTA DE TRANSFERENCIA DE PRODUCTO FORESTAL Y/O DE FAUNA SILVESTRE",titulo);
            titlePara1.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara1));
            document.add(new Paragraph("\r\n\r\n"));

            String nroActa = transferencia.get(0).getNroActaTraslado()==null?transferencia.get(0).getNroActaTransferencia():transferencia.get(0).getNroActaTraslado();

            Paragraph titlePara2 = new Paragraph("ACTA DE TRANSFERENCIA N° "+nroActa+" - "+transferencia.get(0).getLstTransferenciaDetalle().get(0).getNombreAlmacen(),titulo);
            titlePara2.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara2));
            document.add(new Paragraph("\r\n\r\n"));

            Paragraph preface = new Paragraph();
            preface.add(new Paragraph("En el almacén del Puesto de Control Forestal y de Fauna silvestre "+transferencia.get(0).getLstTransferenciaDetalle().get(0).getNombreAlmacen()
                                      +  ", ubicado en "+almacen.getDireccionAlmacen()+", Distrito de "+almacen.getDepartamento()+", Provincia de "+almacen.getProvincia()+", Distrito de "+almacen.getDistrito()+
                                         ", siendo las "+this.format(dateNow,"HH:mm")+" horas, del día "+this.format(dateNow,"dd")+" de "+this.retomaMes(Integer.parseInt(this.format(dateNow,"MM")))
                                      +  " del "+this.format(dateNow,"YYYY"), subTituloTabla));
           // addEmptyLine(preface, 1);
            document.add(preface);


/*
            Paragraph titlePara1 = new Paragraph("Año de la Unidad, la Paz y el Desarrollo", titulo);
            titlePara1.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara1));
            document.add(new Paragraph("\r\n"));
            Paragraph titlePara2 = new Paragraph("ACTA DE INTERVENCIÓN ", titulo);
            titlePara2.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara2));
            document.add(new Paragraph("\r\n\r\n"));
            PdfPTable table0 = createActaContenido(writer, idRecurso);
            document.add(table0);

            Rectangle two = new Rectangle(595.0F, 842.0F);
            document.setPageSize(two);
            document.setMargins(60, 60, 40, 40);
            document.newPage();
            Paragraph titlePara3 = new Paragraph("ACTA DE INTERVENCIÓN ", titulo);
            titlePara3.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara3));
            document.add(new Paragraph("\n"));
            PdfPTable table2 = createActaContenidoCubicacion(writer, idRecurso);
            document.add(table2);

            document.add(new Paragraph("\n"));
            PdfPTable table3 = createNoMaderable(writer, idRecurso);
            document.add(table3);

            document.add(new Paragraph("\n"));
            PdfPTable table4 = createFauna(writer, idRecurso);
            document.add(table4);*/


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }


    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }


    public String retomaMes(int Mes)
    {
        String[] meses = {"Enero","Febrero","Marzo","Abril","Mayo","Junio","Julio","Agosto"," ;Septiembre"
                ,"Octubre","Noviembre","Diciemrbre"};
        String retornaMes = "";
        retornaMes = meses[Mes-1];
        return retornaMes;
    }

}
