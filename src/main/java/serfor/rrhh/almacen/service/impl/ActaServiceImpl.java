package serfor.rrhh.almacen.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import serfor.rrhh.almacen.entity.*;
import serfor.rrhh.almacen.repository.ActaRepository;
import serfor.rrhh.almacen.repository.CubicacionRepository;
import serfor.rrhh.almacen.repository.RecursoRepository;
import serfor.rrhh.almacen.service.ActaService;
import com.lowagie.text.*;
import com.lowagie.text.pdf.PdfPCell;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.docx4j.openpackaging.packages.WordprocessingMLPackage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.stereotype.Service;

import java.io.*;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ActaServiceImpl implements ActaService {

    @Autowired
    private ActaRepository actaRepository;
    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private CubicacionRepository cubicacionRepository;
    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
    Font titulo = new Font(Font.HELVETICA, 11f, Font.BOLD);
    Font subTitulo = new Font(Font.HELVETICA, 10f, Font.BOLD);
    Font contenido = new Font(Font.HELVETICA, 8f, Font.COURIER);
    Font cabecera = new Font(Font.HELVETICA, 9f, Font.BOLD);
    Font cabeceraPeque= new Font(Font.HELVETICA, 7f, Font.BOLD);
    Font subTituloTabla = new Font(Font.HELVETICA, 10f, Font.COURIER);
    Font subTitulo2 = new Font(Font.HELVETICA, 10f, Font.COURIER);
    Font letraPeque = new Font(Font.HELVETICA, 6f, Font.COURIER);


    @Override
    public ResultClassEntity RegistroActa(ActaEntity acta) throws Exception {
        return actaRepository.RegistroActa(acta);
    }

    @Override
    public ResultClassEntity<ActaEntity> ListarActa(Integer nuIdRecurso) throws Exception {
        return actaRepository.ListarActa(nuIdRecurso);
    }

    @Override
    public ResultClassEntity ActualizarFlag(ActaEntity acta) throws Exception {
        return actaRepository.ActualizarFlag(acta);
    }

    private XWPFDocument getDoc(String nameFile) throws NullPointerException, IOException {
        InputStream file = getClass().getClassLoader().getResourceAsStream(nameFile);
        return new XWPFDocument(file);
    }

    @Override
    public ByteArrayResource consolidadoActa_PDF(Integer idRecurso) throws Exception {
        XWPFDocument doc = getDoc("formatoActa.docx");

        ByteArrayOutputStream b = new ByteArrayOutputStream();
        doc.write(b);
        doc.close();

        /*
         * *********************************** USO DE LIBRERIA PDF
         *************************************/
        InputStream myInputStream = new ByteArrayInputStream(b.toByteArray());
        WordprocessingMLPackage wordMLPackage = WordprocessingMLPackage.load(myInputStream);
        File archivo = File.createTempFile("consolidadoActa", ".pdf");

        FileOutputStream os = new FileOutputStream(archivo);

        createPdfActa(os, idRecurso);
        os.flush();
        os.close();
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        byte[] fileContent = Files.readAllBytes(archivo.toPath());
        return new ByteArrayResource(fileContent);

    }

    public void createPdfActa(FileOutputStream os, Integer idRecurso) {
        Document document = new Document(PageSize.A4, 40, 40, 40, 40);
        document.setMargins(60, 60, 40, 40);
        try {
            PdfWriter writer = PdfWriter.getInstance(document, os);

            document.open();
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
            document.add(table4);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            document.close();
        }
    }

    public PdfPTable createActaContenido(PdfWriter writer, Integer idRecurso) throws Exception {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(120);
        float[] values = new float[5];
        values[0] = 50;
        values[1] = 70;
        values[2] = 60;
        values[3] = 60;
        values[4] = 140;
        table.setWidths(values);
        PdfPCell cell;
        int size = 20;

        ResultClassEntity<ActaEntity> listActa =ListarActa(idRecurso);
        ActaEntity acta = listActa.getData();

        cell = new PdfPCell(new Paragraph("FECHA ", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getFechaIntervencion()!=null?formatter.format(acta.getFechaIntervencion()):"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("HORA ", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getHora()!=null?acta.getHora():"", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("LUGAR", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getLugar()!=null?acta.getLugar():"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Nombre de la persona intervenida ", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getNombreIntervenido()!=null?acta.getNombreIntervenido():"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("N° DNI", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getNumeroDNI()!=null?acta.getNumeroDNI():"", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Domicilio de la persona intervenida", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getDomicilioIntervenido()!=null?acta.getDomicilioIntervenido():"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Descripción de los hechos", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getDescripcionHechos()!=null?acta.getDescripcionHechos():"", subTitulo2));
        cell.setFixedHeight(100);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Conducta Infractora que se imputa", subTitulo));
        cell.setFixedHeight(size);
        cell.setRowspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getConductaInfractora()!=null?acta.getConductaInfractora():"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setRowspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("", subTitulo2));
        cell.setFixedHeight(size);
        cell.setRowspan(3);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Leve", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        String tipo =acta.getTipoInfraccion()!=null?acta.getTipoInfraccion():"";

        cell = new PdfPCell(new Paragraph(tipo.equals("Leve")?"(X)":"()", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Grave", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(tipo.equals("Grave")?"(X)":"()", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Muy Grave", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(tipo.equals("Muy Grave")?"(X)":"()", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Sanción", subTitulo));
        cell.setFixedHeight(size);
        cell.setRowspan(3);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Tipo de Sanción que corresponda", subTitulo));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Rango de la sanción/Monto(en caso de multa)", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Sustento Normativo", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        String sancion =acta.getSancion()!=null?acta.getSancion():"";

        cell = new PdfPCell(new Paragraph("Amonestación", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(sancion.equals("Amonestacion")?"(X)":"()", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getMontoMulta()!=null?acta.getMontoMulta():"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getSustentoNormativoMulta()!=null?acta.getSustentoNormativoMulta():"", subTitulo2));
        cell.setFixedHeight(size);
        cell.setRowspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Multa", subTitulo));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(sancion.equals("Multa")?"(X)":"()", subTitulo2));
        cell.setFixedHeight(size);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Autoridad instructora", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getNombreAutoridadInstructora()!=null?acta.getNombreAutoridadInstructora():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Sustento normativo", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getSustentoNormativoInstructora()!=null?acta.getSustentoNormativoInstructora():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Autoridad decisora", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getAtutoridadDecisora()!=null?acta.getAtutoridadDecisora():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Sustento normativo", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getSustentoNormativoDecisora()!=null?acta.getSustentoNormativoDecisora():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Medios de prueba", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getMediosPrueba()!=null?acta.getMediosPrueba():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Plazo para presentar descargos", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getPlazoPresentacionEncargo()!=null?acta.getPlazoPresentacionEncargo():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tDe acuerdo al artículo 126° de la Ley N° 29763, Ley Forestal y de Fauna Silvestre...", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(5);
        cell.setBorder(0);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(5);
        cell.setBorder(0);
        table.addCell(cell);


        cell = new PdfPCell(new Paragraph("Tipo de medida provisional", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tJustificación", subTitulo));
        cell.setFixedHeight(50);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getMedidaProvisional()!=null?acta.getMedidaProvisional():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getJustificacion()!=null?acta.getJustificacion():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("\t\t\t\t\t\t\t\t\t\t\t\t\t\t\t\tANEXO(de ser necesario)...", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(5);
        cell.setBorder(0);
        table.addCell(cell);

       /* cell = new PdfPCell(new Paragraph("TIPO DE PRODUCTO O ESPECIMEN", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("\t\t\t\t\t\t\tESPECIE", subTitulo));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("\t\t\t\t\t\t\tUNIDAD", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("\t\t\t\t\t\t\tCANTIDAD/VOLUMEN", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getTipoEspecimen()!=null?acta.getTipoEspecimen():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getEspecie()!=null?acta.getEspecie():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getUnidad()!=null?acta.getUnidad():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getCantidadVolumen()!=null?acta.getCantidadVolumen():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);
*/
        cell = new PdfPCell(new Paragraph("", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(5);
        cell.setBorder(0);
        table.addCell(cell);


        cell = new PdfPCell(new Paragraph("Observaciones", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getObservaciones()!=null?acta.getObservaciones():"", subTitulo2));
        cell.setFixedHeight(100);
        cell.setColspan(4);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("AUTORIDAD INSTRUCTORA", subTitulo));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("ADMINISTRADO", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("PARTICIPANTES/TESTIGOS", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Nombres y apellidos", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getAutoridadInstructora()!=null?acta.getAutoridadInstructora():"", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getNombreAdministrado()!=null?acta.getNombreAdministrado():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph(acta.getNombreTestigo()!=null?acta.getNombreTestigo():"", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("Firma", subTitulo));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("", subTitulo2));
        cell.setFixedHeight(50);
        cell.setColspan(2);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        cell = new PdfPCell(new Paragraph("", subTitulo2));
        cell.setFixedHeight(50);
        table.addCell(cell);

        return table;
    }

    public PdfPTable createActaContenidoCubicacion(PdfWriter writer, Integer idRecurso) throws Exception {
        PdfPTable table = new PdfPTable(7);
        table.setWidthPercentage(120);
        PdfPCell cell;
        int size = 50;

        Page p = new Page(Long.valueOf(1), Long.valueOf(1000), null, "ASC");

        List<RecursoProductoEntity> listaEspeciesRecurso = recursoRepository.ListarRecursoEspeciePDF(null, idRecurso, p);
        Integer contador=0;

        cell = new PdfPCell(new Paragraph("RECURSOS MADERABLES", titulo));
        cell.setFixedHeight(30);
        cell.setColspan(7);
        cell.setBorder(0);
        table.addCell(cell);

        if (!listaEspeciesRecurso.isEmpty()) {
            listaEspeciesRecurso = listaEspeciesRecurso.stream().filter(q-> q.getType().equals("MAD")).collect(Collectors.toList());
            for (RecursoProductoEntity recursoProductoEntity : listaEspeciesRecurso) {
                contador++;
                String comun = recursoProductoEntity.getNombreComun() != null ? recursoProductoEntity.getNombreComun() : "";
                String cientifico = recursoProductoEntity.getNombreCientifico() != null ? recursoProductoEntity.getNombreCientifico() : "";
                String nombreCompleto = comun +" - " +cientifico;
                cell = new PdfPCell(new Paragraph(contador +" "+nombreCompleto, subTitulo2));
                cell.setFixedHeight(30);
                cell.setColspan(7);
                cell.setBorder(0);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("TIPO DE PRODUCTO ", subTitulo));
                cell.setFixedHeight(25);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE COMÚN", subTitulo));
                cell.setFixedHeight(25);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE CIENTÍFICO", subTitulo));
                cell.setFixedHeight(25);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("UNIDAD", subTitulo));
                cell.setFixedHeight(25);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("CANTIDAD", subTitulo));
                cell.setFixedHeight(25);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getDesctipoProducto() != null ? recursoProductoEntity.getDesctipoProducto() : "", subTitulo2));
                cell.setFixedHeight(size);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getNombreComun() != null ? recursoProductoEntity.getNombreComun() : "", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getNombreCientifico() != null ? recursoProductoEntity.getNombreCientifico() : "", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getUnidadMedida() != null ? recursoProductoEntity.getUnidadMedida() : "", subTitulo2));
                cell.setFixedHeight(size);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getTxCantidadProducto() != null ? recursoProductoEntity.getTxCantidadProducto() : "", subTitulo2));
                cell.setFixedHeight(size);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(7);
                cell.setBorder(0);
                table.addCell(cell);

                List<CubicacionEntity> cubicationList = cubicacionRepository.ListarCubicacionPDF(recursoProductoEntity.getNuIdRecursoProducto(), p);

                if (!cubicationList.isEmpty()) {

                    if(recursoProductoEntity.getTipoProducto().equals("ROLL")){
                        cell = new PdfPCell(new Paragraph("CANTIDAD", subTitulo));
                        cell.setFixedHeight(25);
                        cell.setColspan(2);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("DIÁMETRO", subTitulo));
                        cell.setFixedHeight(25);
                        cell.setColspan(2);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("LONGITUD", subTitulo));
                        cell.setFixedHeight(25);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("Volumen M3", subTitulo));
                        cell.setFixedHeight(25);
                        cell.setColspan(2);
                        table.addCell(cell);

                        for (CubicacionEntity cubication : cubicationList) {

                            cell = new PdfPCell(new Paragraph(cubication.getCantidad() != null ? String.valueOf(cubication.getCantidad()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            cell.setColspan(2);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getDiametroPromedio() != null ? String.valueOf(cubication.getDiametroPromedio()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            cell.setColspan(2);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getLongitud() != null ? String.valueOf(cubication.getLongitud()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getVolumenM3() != null ? String.valueOf(cubication.getVolumenM3()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            cell.setColspan(2);
                            table.addCell(cell);

                        }

                        cell = new PdfPCell(new Paragraph("", subTitulo2));
                        cell.setFixedHeight(size);
                        cell.setColspan(7);
                        cell.setBorder(0);
                        table.addCell(cell);

                    }else if(recursoProductoEntity.getTipoProducto().equals("ACE")){
                        cell = new PdfPCell(new Paragraph("CANTIDAD", subTitulo));
                        cell.setFixedHeight(25);
                        cell.setColspan(2);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("ESPESOR", subTitulo));
                        cell.setFixedHeight(25);
                        cell.setColspan(2);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("ANCHO", subTitulo));
                        cell.setFixedHeight(25);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("LARGO", subTitulo));
                        cell.setFixedHeight(25);
                        table.addCell(cell);

                        cell = new PdfPCell(new Paragraph("Volumen PT", subTitulo));
                        cell.setFixedHeight(25);
                        table.addCell(cell);

                        for (CubicacionEntity cubication : cubicationList) {

                            cell = new PdfPCell(new Paragraph(cubication.getCantidad() != null ? String.valueOf(cubication.getCantidad()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            cell.setColspan(2);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getEspesor() != null ? String.valueOf(cubication.getEspesor()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            cell.setColspan(2);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getAncho() != null ? String.valueOf(cubication.getAncho()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getLargo() != null ? String.valueOf(cubication.getLargo()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            table.addCell(cell);

                            cell = new PdfPCell(new Paragraph(cubication.getVolumenPT() != null ? String.valueOf(cubication.getVolumenPT()) : "", subTitulo2));
                            cell.setFixedHeight(size);
                            table.addCell(cell);

                        }

                        cell = new PdfPCell(new Paragraph("", subTitulo2));
                        cell.setFixedHeight(size);
                        cell.setColspan(7);
                        cell.setBorder(0);
                        table.addCell(cell);

                    }

                }

            }

        }

        return table;
    }

    public PdfPTable createNoMaderable(PdfWriter writer, Integer idRecurso) throws Exception {
        PdfPTable table = new PdfPTable(6);
        table.setWidthPercentage(120);
        PdfPCell cell;
        int size = 50;

        Page p = new Page(Long.valueOf(1), Long.valueOf(1000), null, "ASC");

        List<RecursoProductoEntity> listaEspeciesRecurso = recursoRepository.ListarRecursoEspeciePDF(null, idRecurso, p);

        cell = new PdfPCell(new Paragraph("RECURSOS NO MADERABLES", titulo));
        cell.setFixedHeight(30);
        cell.setColspan(6);
        cell.setBorder(0);
        table.addCell(cell);

        if (!listaEspeciesRecurso.isEmpty()) {
            listaEspeciesRecurso = listaEspeciesRecurso.stream().filter(q-> q.getType().equals("NOMAD")).collect(Collectors.toList());
            for (RecursoProductoEntity recursoProductoEntity : listaEspeciesRecurso) {

                cell = new PdfPCell(new Paragraph("NOMBRE CIENTÍFICO", subTitulo));
                cell.setFixedHeight(25);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE COMÚN", subTitulo));
                cell.setFixedHeight(25);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("CANTIDAD", subTitulo));
                cell.setFixedHeight(25);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("UNIDAD", subTitulo));
                cell.setFixedHeight(25);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getNombreCientifico() != null ? recursoProductoEntity.getNombreCientifico() : "", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getNombreComun() != null ? recursoProductoEntity.getNombreComun() : "", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getTxCantidadProducto() != null ? recursoProductoEntity.getTxCantidadProducto() : "", subTitulo2));
                cell.setFixedHeight(size);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getUnidadMedida() != null ? recursoProductoEntity.getUnidadMedida() : "", subTitulo2));
                cell.setFixedHeight(size);
                table.addCell(cell);


                cell = new PdfPCell(new Paragraph("", subTitulo2));
                cell.setFixedHeight(30);
                cell.setColspan(6);
                cell.setBorder(0);
                table.addCell(cell);

            }

        }

        return table;
    }

    public PdfPTable createFauna(PdfWriter writer, Integer idRecurso) throws Exception {
        PdfPTable table = new PdfPTable(5);
        table.setWidthPercentage(120);
        PdfPCell cell;
        int size = 50;

        Page p = new Page(Long.valueOf(1), Long.valueOf(1000), null, "ASC");

        List<RecursoProductoEntity> listaEspeciesRecurso = recursoRepository.ListarRecursoEspeciePDF(null, idRecurso, p);

        cell = new PdfPCell(new Paragraph("FAUNA", titulo));
        cell.setFixedHeight(30);
        cell.setColspan(5);
        cell.setBorder(0);
        table.addCell(cell);

        if (!listaEspeciesRecurso.isEmpty()) {
            listaEspeciesRecurso = listaEspeciesRecurso.stream().filter(q-> q.getType().equals("FA")).collect(Collectors.toList());
            for (RecursoProductoEntity recursoProductoEntity : listaEspeciesRecurso) {

                cell = new PdfPCell(new Paragraph("NOMBRE CIENTÍFICO", subTitulo));
                cell.setFixedHeight(25);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("NOMBRE COMÚN", subTitulo));
                cell.setFixedHeight(25);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("CANTIDAD", subTitulo));
                cell.setFixedHeight(25);
                table.addCell(cell);


                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getNombreCientifico() != null ? recursoProductoEntity.getNombreCientifico() : "", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getNombreComun() != null ? recursoProductoEntity.getNombreComun() : "", subTitulo2));
                cell.setFixedHeight(size);
                cell.setColspan(2);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph(recursoProductoEntity.getTxCantidadProducto() != null ? recursoProductoEntity.getTxCantidadProducto() : "", subTitulo2));
                cell.setFixedHeight(size);
                table.addCell(cell);

                cell = new PdfPCell(new Paragraph("", subTitulo2));
                cell.setFixedHeight(30);
                cell.setColspan(6);
                cell.setBorder(0);
                table.addCell(cell);

            }

        }

        return table;
    }
}
