/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.wcs.lemurs.service;

import java.util.List;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.wcs.lemurs.model.Kml;

/**
 *
 * @author zacharie
 */
@Service
@Transactional
public class KmlService extends DarwinCoreService {

    public static final String KML_GID_NAME = "gid";

    public static final String KML_LABEL_NAME = "name";

    private static final String KML_FOLDER_HEADER = "<Folder><name>sql_statement</name>";

    private static final String KML_FOLDER_FOOTER = "</Folder>";

    private static final String KML_DOCUMENT_HEADER = "<?xml version=\"1.0\" encoding=\"utf-8\" ?>"
            + "<kml xmlns=\"http://www.opengis.net/kml/2.2\">"
            + "<Document>";

    private static final String KML_DOCUMENT_FOOTER = "</Document></kml>";

    private static final String KML_SCHEMA_BLOC = "<Schema name=\"sql_statement\" id=\"sql_statement\">"
            + "<SimpleField name=\"" + KML_GID_NAME + "\" type=\"int\"></SimpleField>"
            + "<SimpleField name=\"" + KML_LABEL_NAME + "\" type=\"string\"></SimpleField>"
            + "</Schema>";

    public static final String GIDS_ITEM_SEPARATOR = "|";
    public static final String TABLE_PARAM_NAME = "table";
    public static final String GIDS_PARAM_NAME = "gids";

    /**
     * Recupérer les urls pour télécharger les fichiers kml (1 fichier kml par
     * table)
     *
     * @param tableGidsMap
     * @return
     */
//    public static Set<String> getKmlFileUrl(Map<String, List<Integer>> tableGidsMap) {
//        StringBuilder gidsParTables = new StringBuilder();
//        Set<String> urls = new HashSet<String>();
//        if (tableGidsMap.size() > 0) {
//            for (Entry<String, List<Integer>> entry : tableGidsMap.entrySet()) {
//                StringBuilder url = new StringBuilder(GWT.getModuleBaseURL() + "kmlfile");
//                String tableParamName = TABLE_PARAM_NAME;
//                String tableParamValue = entry.getKey();
//                if (url.indexOf("?") == -1) {
//                    url.append("?");
//                } else {
//                    url.append("&");
//                }
//                url.append(tableParamName).append("=").append(tableParamValue);//un url par table
//                //gids
//                int gidIndex = 0;
//                gidsParTables.setLength(0);
//                for (Integer gid : entry.getValue()) {
//                    if (gidIndex > 0) {
//                        gidsParTables.append(GIDS_ITEM_SEPARATOR);
//                    }
//                    gidsParTables.append(gid);
//                    gidIndex++;
//                }
//                url.append("&").append(GIDS_PARAM_NAME).append("=").append(gidsParTables.toString());
//                urls.add(url.toString());
//            }
//        }
//        return urls;
//    }

    /**
     *
     * @param kmls
     */
    public static String getKMLString(List<Kml> stAsKmlResults) {
        StringBuilder kml = new StringBuilder(KML_DOCUMENT_HEADER);
        kml.append(KML_FOLDER_HEADER);
        for (Kml kmlGeom : stAsKmlResults) {
            String placemark = asPlacemarkBloc(kmlGeom);
            kml.append(placemark);
        }
        kml.append(KML_FOLDER_FOOTER);
        kml.append(KML_SCHEMA_BLOC);
        kml.append(KML_DOCUMENT_FOOTER);
        //System.out.println(kml);
        return kml.toString();
    }

    /**
     * <Placemark>
     * <Style><LineStyle><color>ff0000ff</color></LineStyle><PolyStyle><fill>0</fill></PolyStyle></Style>
     * <ExtendedData><SchemaData schemaUrl="#sql_statement">
     * <SimpleData name="gid">1</SimpleData>
     * <SimpleData name="nom_region">ALAOTRAMANGORO</SimpleData>
     * <SimpleData name="first_nom_">TOAMASINA</SimpleData>
     * </SchemaData></ExtendedData>
     * <MultiGeometry><Polygon><outerBoundaryIs><LinearRing><coordinates>48.045268047911982,-19.706459367377235
     * 48.031740918917407,-19.773852573404078
     * 47.938516976412352,-19.75454305568628
     * 47.909237639802541,-19.803168607591623
     * 47.869924623399264,-19.78254176468851
     * 47.866249852061294,-19.746267774090128
     * 47.836768276402765,-19.718436854404779
     * 47.837418599868123,-19.628811534366641
     * 47.863141719459094,-19.60748348798835
     * 47.857125073489598,-19.535782821273127
     * 47.903023723886683,-19.510074310061107
     * 47.880307785075658,-19.440055058022029
     * 47.947055512272968,-19.362616375147358
     * 47.93902542036534,-19.298531038224606
     * 47.956471414547742,-19.223633413742743
     * 47.904175584877322,-19.209902611330879
     * 47.897051734274847,-19.172215908154751
     * 47.923999922077158,-19.154122197851841
     * 47.893869369959965,-19.103230117922454
     * 47.943261944937461,-18.978550013957282
     * 47.94690322571163,-18.866292478690116
     * 47.926935840223891,-18.763962037355483
     * 47.94436374380701,-18.699879551633455
     * 47.93042307382612,-18.657957152698568
     * 47.946706523582769,-18.626664059500076
     * 47.971693338888379,-18.626158876981663
     * 48.0001246050155,-18.399687072558365
     * 48.036811019011722,-18.379802265423223
     * 48.018445601929997,-18.281655644558985
     * 48.00203094811323,-18.275874121208435
     * 48.007534804648593,-18.181604468272678
     * 47.988994888211735,-18.161514047170108
     * 48.005151694074982,-18.051207111564196
     * 47.966364286405124,-18.054510697429905
     * 47.968409031918974,-18.028395748346629
     * 47.930830580957299,-17.977112105018552
     * 47.916960066199337,-17.978413012049334
     * 47.910191980873073,-17.94741911951801
     * 47.934839365132873,-17.911958340725786
     * 47.936194680580542,-17.864207053988359
     * 47.959880756424958,-17.812336039657875
     * 47.949787761337454,-17.784864096397786
     * 47.977505019475515,-17.729642405846317
     * 47.999927780173799,-17.724621250237302
     * 47.989413782515022,-17.693061385488534
     * 48.039962484370641,-17.67937099079354
     * 48.05197214601715,-17.650085255664624
     * 48.020192314492782,-17.643105285894286
     * 47.969544991731468,-17.596222601377608
     * 47.982829427282958,-17.556872269533869
     * 47.956881242184203,-17.559800999465384
     * 47.918104210108517,-17.482440454254618
     * 47.923731829735011,-17.461391986778754
     * 47.93672206439777,-17.471335136768502
     * 47.96916892760386,-17.439996090857793
     * 47.992111075175153,-17.448887083743141
     * 48.021643488904267,-17.39338987836808
     * 48.069053358181321,-17.405394589171813
     * 48.072704111058265,-17.421946067608943
     * 48.10279260772414,-17.410808299270908
     * 48.103360475036709,-17.338959885487817
     * 48.151155770667934,-17.320816751916254
     * 48.138963818772325,-17.26051918686969
     * 48.197287790029854,-17.183250706064477
     * 48.180062075676851,-17.057954896754723
     * 48.136743480437474,-17.070121734155535
     * 48.083963438858824,-17.04143545119997
     * 48.052194047445717,-16.989467346511251
     * 48.071001903964557,-16.971907991676417
     * 48.048069954096768,-16.949004297494938
     * 48.065635721587142,-16.902363293224337
     * 48.003802120461302,-16.854169170314012
     * 48.011731790091638,-16.833980374564458
     * 47.987057000853675,-16.788890780464826
     * 47.925577994098894,-16.727100050696293
     * 47.945097463472912,-16.703897910805704
     * 47.937377810424195,-16.667892247571721
     * 47.949645691092527,-16.642215726821213
     * 47.893196537946309,-16.56219720275493
     * 47.895466711758729,-16.487827578105978
     * 47.918456302811585,-16.462381784100501
     * 47.91326242639817,-16.435378844721892
     * 47.933715268009138,-16.424845833920863
     * 47.907162617701452,-16.34669161320171
     * 47.950658621368369,-16.291577699567654
     * 47.956257330778513,-16.24055690264462
     * 47.981218630602498,-16.258895426718819
     * 48.019546148384528,-16.250062949704976
     * 48.114106297161982,-16.282960144209344
     * 48.232553845729882,-16.24052879395828
     * 48.283670874916282,-16.246321592551485
     * 48.317023104331156,-16.285754301231481
     * 48.372959854310643,-16.312088407879784
     * 48.378455093914766,-16.35494494838029
     * 48.390789857508707,-16.339715636280022
     * 48.455848770499117,-16.338732222508739
     * 48.496183349293567,-16.399413231811341
     * 48.533636275973365,-16.404629218598835
     * 48.590613126178141,-16.448432768506741
     * 48.586339248235667,-16.464494672982159
     * 48.618172653083761,-16.488708582716949
     * 48.604168078925376,-16.49704808980211
     * 48.613348430302509,-16.515513279668085
     * 48.637536379292364,-16.51091029124261
     * 48.616548635905062,-16.552893270887193
     * 48.655651096781092,-16.592473885821779
     * 48.602945304569623,-16.657509348389279
     * 48.615119890067561,-16.68260793885969
     * 48.621585080021276,-16.664616462899659
     * 48.658980142291398,-16.658103991982465
     * 48.690589790651607,-16.689722052007362
     * 48.719235573413791,-16.6641493051245
     * 48.75275628457068,-16.660116645868261
     * 48.785809314659993,-16.683200661053629
     * 48.860262869490043,-16.671607071073037
     * 48.9339973518763,-16.68558493763949
     * 48.877089641552779,-16.731123264770162
     * 48.87457060086966,-16.782664741824544
     * 48.822161906604201,-16.857615548471117
     * 48.815326497829361,-16.879244735365031
     * 48.83016378196033,-16.876090369008402
     * 48.822592882219702,-16.892517904611019
     * 48.839771777413709,-16.895282326466269
     * 48.836510818735064,-16.951954897516597
     * 48.78275679772716,-17.066694364763709
     * 48.79576464232926,-17.171038186166236
     * 48.819696905973693,-17.169768609630303
     * 48.822193722588644,-17.192522034383515
     * 48.799920468194983,-17.209103332785901
     * 48.810882103978585,-17.235865041332477
     * 48.802438534318071,-17.288438642234858
     * 48.782457703407324,-17.30773287412428
     * 48.78358734782595,-17.362991886022868
     * 48.801264933056125,-17.388925866318623
     * 48.836100499925756,-17.370102364031528
     * 48.826064111288531,-17.434233706301036
     * 48.802621811363458,-17.4213995279739
     * 48.795003551848623,-17.437409901156379
     * 48.831060998210383,-17.471284016811708
     * 48.78554935928819,-17.491951287242209
     * 48.803865826804319,-17.54648254225323
     * 48.74612367749188,-17.557701630127546
     * 48.737994994218226,-17.601671415704555
     * 48.689660857985984,-17.615417751841299
     * 48.681398492850711,-17.638273791317722
     * 48.73056756101181,-17.669736210675598
     * 48.823879590895565,-17.652824693922209
     * 48.818551958878153,-17.69543003673186
     * 48.848910988900649,-17.697928716506514
     * 48.823280390105744,-17.75428726583846
     * 48.848053002086452,-17.775200221108928
     * 48.816517377604832,-17.802473503723025
     * 48.836912120774848,-17.879093200502602
     * 48.805536698837756,-17.872889183850884
     * 48.796038857892569,-17.898709874687849
     * 48.76773382697737,-17.867179920005942
     * 48.737304592643213,-17.90322355838866
     * 48.723286533743412,-17.899618640288008
     * 48.715500420665357,-17.923216105039536
     * 48.689434660425341,-17.886524953894011
     * 48.656823687102609,-17.897459292977473
     * 48.678448855116578,-17.969357677789056
     * 48.723174851990258,-17.988563907547352
     * 48.719620647031043,-18.033134260534791
     * 48.699683732889461,-18.062830437628467
     * 48.730336122861708,-18.079642260928708
     * 48.735685413942697,-18.058840149563821
     * 48.757215572763492,-18.052995303370498
     * 48.77561177855506,-18.074579846051616
     * 48.789768898239949,-18.196329940922467
     * 48.73614591521401,-18.212026949097616
     * 48.721714375421932,-18.244513381740536
     * 48.704484543600969,-18.235269703737185
     * 48.675699625893287,-18.248454319143494
     * 48.661124108196027,-18.234669547410764
     * 48.618415565801172,-18.258505408421243
     * 48.58760851942597,-18.238447723254588
     * 48.567638891357454,-18.251087118435201
     * 48.556479724759605,-18.234930771686006
     * 48.536475308296787,-18.306042535811542
     * 48.500888216414559,-18.323615930472887
     * 48.469646347034214,-18.393257061098399
     * 48.499267462251169,-18.520873878887389
     * 48.515953665853338,-18.533147723354205
     * 48.503722458772764,-18.562857030437765
     * 48.525036988525926,-18.564303266404544
     * 48.486073266728837,-18.614774019794375
     * 48.499773591960945,-18.662938873780821
     * 48.520531792995968,-18.663616483566575
     * 48.5312782917985,-18.685105884806418
     * 48.505626176417444,-18.714899928101335
     * 48.541080835385991,-18.729849812159046
     * 48.55160265436016,-18.809119173232546
     * 48.586986118394144,-18.812741897574064
     * 48.617653549080508,-18.836254402803721
     * 48.626365208479641,-18.875769231873139
     * 48.606381706853092,-18.921584729274368
     * 48.627224327976108,-18.937775045121477
     * 48.630019554109758,-18.976820870189016
     * 48.659825695695424,-18.996350549912048
     * 48.643937078563624,-19.049084616391962
     * 48.684454554046233,-19.070054329045846
     * 48.653260876315898,-19.110385904239575
     * 48.684793007614466,-19.136937537727952
     * 48.675451110310171,-19.185210262528074
     * 48.630286071099192,-19.147798466448247
     * 48.613223336644751,-19.173211836312355
     * 48.580826401665185,-19.165960633888925
     * 48.591942140771401,-19.245018848269716
     * 48.577637177909793,-19.261421886107428
     * 48.530116416542782,-19.253055223596228
     * 48.513742295029118,-19.325492196375379
     * 48.488381262059775,-19.348165776233586
     * 48.473669231389465,-19.339512004709
     * 48.453842242757915,-19.356131739157966
     * 48.376118715238121,-19.307865794957095
     * 48.364280513424411,-19.362957887620805
     * 48.407218545978203,-19.51750796611044
     * 48.386187291359533,-19.541892218101456
     * 48.345308246280929,-19.537970622056271
     * 48.343067234261525,-19.525908084113976
     * 48.312604539766866,-19.53828928005877
     * 48.324911526927913,-19.571564260721065
     * 48.301257837173829,-19.691627127515051
     * 48.318979620838583,-19.742448724312997
     * 48.29723570278918,-19.79588137409646
     * 48.27307115614046,-19.811255511289456
     * 48.269301830570086,-19.792668908859216
     * 48.232066131581753,-19.780809174051203
     * 48.216153631959699,-19.806040060273514
     * 48.160313152133035,-19.810193697398063
     * 48.108766045952244,-19.770748742287559
     * 48.093926959718267,-19.778413184580192
     * 48.045268047911982,-19.706459367377235</coordinates></LinearRing></outerBoundaryIs></Polygon></MultiGeometry>
     * </Placemark>
     *
     * @param kml
     */
    private static String asPlacemarkBloc(Kml kml) {
        //TODO changer dynamiquement le style 
        StringBuilder sb = new StringBuilder();
        sb.append("<Placemark>")
                .append("<Style><LineStyle><color>ff0000ff</color></LineStyle><PolyStyle><fill>0</fill></PolyStyle></Style>")
                .append("<ExtendedData><SchemaData schemaUrl=\"#sql_statement\">")
                .append("<SimpleData name=\"" + KML_GID_NAME + "\">").append(kml.getGid()).append("</SimpleData>")
                .append("<SimpleData name=\"" + KML_LABEL_NAME + "\">").append(kml.getName()).append("</SimpleData>")
                .append("</SchemaData></ExtendedData>")
                .append(kml.getGisAsKmlResult());
        sb.append("</Placemark>");
        return sb.toString();
    }
}
