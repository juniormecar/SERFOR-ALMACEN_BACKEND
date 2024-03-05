package serfor.rrhh.almacen.service.impl;

import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.AlmacenEntity;
import serfor.rrhh.almacen.entity.TransferenciaDetalleEntity;
import serfor.rrhh.almacen.entity.TransferenciaEntity;
import serfor.rrhh.almacen.service.ActaSalidaService;
import serfor.rrhh.almacen.service.AlmacenService;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
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
            Image imagen = Image.getInstance("largologo.jpg");

            document.open();

            imagen.setAlignment(Element.ALIGN_LEFT);
            document.add(imagen);
            document.add(new Paragraph("\n"));
            Paragraph titlePara1 = new Paragraph("ACTA DE TRANSFERENCIA DE PRODUCTO FORESTAL Y/O DE FAUNA SILVESTRE",titulo);
            titlePara1.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara1));
            document.add(new Paragraph("\n"));

            String nroActa = transferencia.get(0).getNroActaTraslado()==null?transferencia.get(0).getNroActaTransferencia():transferencia.get(0).getNroActaTraslado();

            Paragraph titlePara2 = new Paragraph("ACTA DE TRANSFERENCIA N° "+nroActa+" - "+transferencia.get(0).getLstTransferenciaDetalle().get(0).getNombreAlmacen(),titulo);
            titlePara2.setAlignment(Element.ALIGN_CENTER);
            document.add(new Paragraph(titlePara2));
            document.add(new Paragraph("\r\n"));

            Paragraph preface = new Paragraph();
            preface.add(new Paragraph("En el almacén del Puesto de Control Forestal y de Fauna silvestre "+transferencia.get(0).getLstTransferenciaDetalle().get(0).getNombreAlmacen()
                                      +  ", ubicado en "+almacen.getDireccionAlmacen()+", Distrito de "+almacen.getDepartamento()+", Provincia de "+almacen.getProvincia()+", Distrito de "+almacen.getDistrito()+
                                         ", siendo las "+transferencia.get(0).getHoraTransferencia()+" horas, del día "+this.format(transferencia.get(0).getFechaTransferencia(),"dd")+" de "+this.retomaMes(Integer.parseInt(this.format(transferencia.get(0).getFechaTransferencia(),"MM")))
                                      +  " del "+this.format(dateNow,"YYYY"), subTituloTabla));
            preface.setAlignment(Element.ALIGN_JUSTIFIED);
            addEmptyLine(preface, 1);

            document.add(preface);
            if(transferencia.get(0).getTipoTransferencia().equals("TPTRANS001")){
                PdfPTable table21 = createMonitoreoTrans01(writer,transferencia);
                document.add(table21);
                document.add(new Paragraph("\n"));
                Paragraph preface2 = new Paragraph();
                preface2.add(new Paragraph("En calidad de transferencia, a "+transferencia.get(0).getNombre()
                        +  ". Quien recoge y traslada los productos paa junra ser utilizado en dicha entidad, acorde al perfil del proyecto presentado, quien a su vez se comppromete a" +
                        " hacer uso de éstos para los fines requeridos.", subTituloTabla));
                preface2.setAlignment(Element.ALIGN_JUSTIFIED);
                addEmptyLine(preface2, 1);
                document.add(preface2);
                Paragraph preface3 = new Paragraph();
                preface3.add(new Paragraph("Los productos forestales, transferidos podran ser revertidos al Servicio Nacional Forestal y de Fauna Silvestre (SERFOR) ATFFS LIMA," +
                        " de comprobarse que en el lapso de dos(2) meses posteriores a la ejecución de la transferencia éstos no hayan sido utilizados para los fines solicitados.", subTituloTabla));
                preface3.setAlignment(Element.ALIGN_JUSTIFIED);
                addEmptyLine(preface3, 1);
                document.add(preface3);
            }else if(transferencia.get(0).getTipoTransferencia().equals("TPTRANS002")){

                AlmacenEntity almacen2 = almacenService.getAlmacen(transferencia.get(0).getNuIdAlmacen());
                PdfPTable table21 = createMonitoreoTrans02(writer,transferencia);
                document.add(table21);
                document.add(new Paragraph("\n"));
                Paragraph preface2 = new Paragraph();
                preface2.add(new Paragraph("En calidad de transferencia, al almacen "+almacen2.getTxNombreAlmacen()
                        +  ". Quien recoge y traslada los productos para ser utilizado en dicho almacen ", subTituloTabla));
                preface2.setAlignment(Element.ALIGN_JUSTIFIED);
                addEmptyLine(preface2, 1);
                document.add(preface2);
            }else if(transferencia.get(0).getTipoTransferencia().equals("TPTRANS003")){

                AlmacenEntity almacen2 = almacenService.getAlmacen(transferencia.get(0).getNuIdAlmacen());
                PdfPTable table21 = createMonitoreoTrans01(writer,transferencia);
                document.add(table21);
                document.add(new Paragraph("\n"));
                Paragraph preface2 = new Paragraph();
                preface2.add(new Paragraph("En calidad de transferencia, al almacen "+almacen2.getTxNombreAlmacen()
                        +  ". Quien recoge y traslada los productos para ser utilizado en dicho almacen ", subTituloTabla));
                preface2.setAlignment(Element.ALIGN_JUSTIFIED);
                addEmptyLine(preface2, 1);
                document.add(preface2);
            }


            Paragraph preface4 = new Paragraph();
            preface4.add(new Paragraph(" Siendo las " +transferencia.get(0).getHoraTransferencia()+" horas del mismo día, en señal de conformidad firman", subTituloTabla));
            preface4.setAlignment(Element.ALIGN_JUSTIFIED);
            addEmptyLine(preface4, 6);
            document.add(preface4);

            PdfPTable table22 = createFirmas(writer,transferencia);
            document.add(table22);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }


    }

    public  PdfPTable createMonitoreoTrans01(PdfWriter writer,List<TransferenciaEntity> transferencia) throws Exception {

        PdfPTable table = new PdfPTable(6);

        try {

            table.setWidthPercentage(95);
            table.setHeaderRows(1);
            PdfPCell cell;

            setearRowsColspanStatic("N° de Acta de Intervención", 1, 1, table);
            setearRowsColspanStatic("N° DE RESOLUCIÓN", 1, 1, table);
            setearRowsColspanStatic("TIPO DE PRODUCTO (DESCRIPCIÓN)", 1, 1, table);
            setearRowsColspanStatic("ESPECIES", 1, 1, table);
            setearRowsColspanStatic("UNIDAD DE MEDIDA", 1, 1, table);
            setearRowsColspanStatic("CANTIDAD", 1, 1, table);


            if(transferencia.get(0).getLstTransferenciaDetalle()!=null && !transferencia.get(0).getLstTransferenciaDetalle().isEmpty()) {
                for (TransferenciaDetalleEntity detalle : transferencia.get(0).getLstTransferenciaDetalle()) {
                    String nombreCientifico = detalle.getNombreCientifico()!=null?detalle.getNombreCientifico():"";
                    String nombreComun = detalle.getNombreComun()!=null?detalle.getNombreComun():"";
                    setearRowsColspanData(detalle.getNumeroActa(), 1, 1, table);
                    setearRowsColspanData(transferencia.get(0).getNroResolucion(), 1, 1, table);
                    setearRowsColspanData(detalle.getTipo(), 1, 1, table);
                    setearRowsColspanData(nombreCientifico +" / "+nombreComun, 1, 1, table);
                    setearRowsColspanData(detalle.getUnidadMedida(), 1, 1, table);
                    setearRowsColspanData(detalle.getTxCantidadProducto(), 1, 1, table);
                }
            }

        } catch (Exception e) {
            log.error("ArchivoPGMFServiceImpl - createMonitoreoPGMFA"+e.getMessage());
        }
        return table;
    }

    public  PdfPTable createMonitoreoTrans02(PdfWriter writer,List<TransferenciaEntity> transferencia) throws Exception {

        PdfPTable table = new PdfPTable(6);

        try {

            table.setWidthPercentage(95);
            table.setHeaderRows(1);
            PdfPCell cell;

            setearRowsColspanStatic("N° de Acta de Intervención", 1, 1, table);
            setearRowsColspanStatic("TIPO DE PRODUCTO (DESCRIPCIÓN)", 1, 1, table);
            setearRowsColspanStatic("ESPECIES", 1, 1, table);
            setearRowsColspanStatic("UNIDAD DE MEDIDA", 1, 1, table);
            setearRowsColspanStatic("CANTIDAD", 1, 1, table);


            if(transferencia.get(0).getLstTransferenciaDetalle()!=null && !transferencia.get(0).getLstTransferenciaDetalle().isEmpty()) {
                for (TransferenciaDetalleEntity detalle : transferencia.get(0).getLstTransferenciaDetalle()) {
                    String nombreCientifico = detalle.getNombreCientifico()!=null?detalle.getNombreCientifico():"";
                    String nombreComun = detalle.getNombreComun()!=null?detalle.getNombreComun():"";
                    setearRowsColspanData(detalle.getNumeroActa(), 1, 1, table);
                    setearRowsColspanData(detalle.getTipo(), 1, 1, table);
                    setearRowsColspanData(nombreCientifico +" / "+nombreComun, 1, 1, table);
                    setearRowsColspanData(detalle.getUnidadMedida(), 1, 1, table);
                    setearRowsColspanData(detalle.getTxCantidadProducto(), 1, 1, table);
                }
            }

        } catch (Exception e) {
            log.error("ArchivoPGMFServiceImpl - createMonitoreoPGMFA"+e.getMessage());
        }
        return table;
    }

    public  PdfPTable createFirmas(PdfWriter writer,List<TransferenciaEntity> transferencia) throws Exception {

        PdfPTable table = new PdfPTable(3);

        try {

            table.setWidthPercentage(100);
            table.setHeaderRows(1);
            PdfPCell cell;

            setearRowsColspanData2("------------------------------------------", 1, 1, table);
            setearRowsColspanData2("------------------------------------------", 1, 1, table);
            setearRowsColspanData2("------------------------------------------", 1, 1, table);
            setearRowsColspanData2("Nombre: ..................................", 1, 1, table);
            setearRowsColspanData2("Nombre: ..................................", 1, 1, table);
            setearRowsColspanData2("Nombre: ..................................", 1, 1, table);
            setearRowsColspanData2("Cargo: ...................................", 1, 1, table);
            setearRowsColspanData2("Cargo: ...................................", 1, 1, table);
            setearRowsColspanData2("Cargo: ...................................", 1, 1, table);
            setearRowsColspanData2("DNI N°: ..................................", 1, 1, table);
            setearRowsColspanData2("DNI N°: ..................................", 1, 1, table);
            setearRowsColspanData2("DNI N°: ..................................", 1, 1, table);


        } catch (Exception e) {
            log.error("ArchivoPGMFServiceImpl - createMonitoreoPGMFA"+e.getMessage());
        }
        return table;
    }

    private static void setearRowsColspanData(String dato, Integer row,Integer colum, PdfPTable table){

        PdfPCell c = new PdfPCell(new Phrase(dato, contenido));

        c.setPadding(5);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setVerticalAlignment(Element.ALIGN_CENTER);
        c.setRowspan(row);
        c.setColspan(colum);
        c.setMinimumHeight(20);
        table.addCell(c);
    }

    private static void setearRowsColspanStatic(String dato, Integer row,Integer colum, PdfPTable table){
        String data = dato==null ? "" : dato;

        PdfPCell c = new PdfPCell(new Phrase(data, contenido));
        java.awt.Color colorBody = new java.awt.Color(242 , 242 , 242);

        c.setBackgroundColor(colorBody);
        c.setPadding(5);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setVerticalAlignment(Element.ALIGN_CENTER);
        c.setRowspan(row);
        c.setColspan(colum);
        c.setMinimumHeight(20);
        table.addCell(c);
    }

    private static void setearRowsColspanData2(String dato, Integer row,Integer colum, PdfPTable table){

        PdfPCell c = new PdfPCell(new Phrase(dato, contenido));

        c.setPadding(5);
        c.setHorizontalAlignment(Element.ALIGN_CENTER);
        c.setVerticalAlignment(Element.ALIGN_CENTER);
        c.setRowspan(row);
        c.setColspan(colum);
        c.setMinimumHeight(20);
        c.setBorder(0);
        table.addCell(c);
    }

    private static void addEmptyLine(Paragraph paragraph, int number) {
        for (int i = 0; i < number; i++) {
            paragraph.add(new Paragraph(" "));
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
