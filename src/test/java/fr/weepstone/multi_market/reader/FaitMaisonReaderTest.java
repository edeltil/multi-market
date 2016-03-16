package fr.weepstone.multi_market.reader;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import au.com.bytecode.opencsv.CSVWriter;
import fr.weepstone.multi_market.dao.Category;
import fr.weepstone.multi_market.model.Product;
import org.junit.Assert;
import org.junit.Test;

//@RunWith(SpringJUnit4ClassRunner.class)
//@SpringApplicationConfiguration(classes = MultiMarket.class)
//@WebAppConfiguration
public class FaitMaisonReaderTest {

    //	@Autowired
    private FaitMaisonReader faitMaisonReader = new FaitMaisonReader();

    private DaWandaReader readerDawanda = new DaWandaReader();

    @Test
    public void pageParsingTest() throws IOException {
        File file = new File(getClass().getClassLoader().getResource("fait_maison_products.html").getFile());
        BufferedReader buffReader = new BufferedReader(new FileReader(file));
        StringBuilder result = new StringBuilder("");

        while (buffReader.ready()) {
            String read = buffReader.readLine();
            result.append(read).append("\n");
        }
        buffReader.close();
        List<Product> productsFM = faitMaisonReader.readProducts(result.toString());
        File fileDW = new File(getClass().getClassLoader().getResource("dawanda_products.html").getFile());
        BufferedReader buffReaderDW = new BufferedReader(new FileReader(fileDW));
        StringBuilder resultDW = new StringBuilder("");

        while (buffReaderDW.ready()) {
            String read = buffReaderDW.readLine();
            resultDW.append(read).append("\n");
        }
        buffReaderDW.close();
        List<Product> productsDW = readerDawanda.readProducts(resultDW.toString());
        Assert.assertTrue(productsFM.size() == 32);
        //		Product product1 = products.get(0);
        //		Assert.assertTrue(product1.getTitle().equals("Tapis de souris bleu pigeon en cuir"));
        //		Assert.assertTrue(product1.getPrice() == 20);
        //		Assert.assertTrue(product1.getDescription().equals("Ce tapis de souris est réalisé à partir de cuir de couleur bleu pigeon. Il a une taille de 15*20cm. Ce cuir est d'une épaisseur de 1.1mm.Sur commande la couleur peut etre disponible."));
        //		Assert.assertTrue(product1.getId().equals("product-161602"));
        //		Assert.assertTrue(product1.getUrl_image().equals("/system/uploads/product_photo/files/000/000/322/410/thumb145q70_P9021070B.JPG"));
        //		Assert.assertTrue(product1.getUrl_product().equals("http://www.fait-maison.com/accesoires/accessoires-maison/akemi_tapis-de-souris-bleu-pigeon-en-cuir.html"));
        //		Assert.assertTrue(product1.getCategories().size() == 1);
        //		Assert.assertTrue(product1.getCategories().get(0).equals("maison"));
        CSVWriter writer = new CSVWriter(new FileWriter("fait_maison_products_generate.csv"), ';');
        CSVWriter writerCat = new CSVWriter(new FileWriter("fait_maison_categories_generate.csv"), ';');
        int nbCategories = 0;
        //        List<String> line = new ArrayList<String>();
        List<Category> categories = new ArrayList<Category>();
        String[] line = new String[54];
        for (int i = 0; i < productsFM.size(); i++) {
            Product product = productsFM.get(i);
            Product productDW = null;
            for (Product prod : productsDW) {
                if (prod.getTitle().equals(product.getTitle())) {
                    productDW = prod;
                }
            }
            line[0] = String.valueOf(i + 1);
            line[1] = String.valueOf("1");
            line[2] = product.getTitle();
            if (productDW != null) {
                line[3] = concatString(productDW.getCategories());
                fillCategorie(categories, productDW.getCategories());
            } else {
                line[3] = concatString(product.getCategories());
                fillCategorie(categories, product.getCategories());
            }
            line[4] = String.valueOf(product.getPrice());
            line[5] = "1";
            line[6] = null;
            line[7] = "1";
            line[8] = null;
            line[9] = null;
            line[10] = null;
            line[11] = null;
            line[12] = "C_" + (i + 1);
            line[13] = "C_" + (i + 1);
            line[14] = "Riku Création";
            line[15] = "Riku Création";
            line[16] = null;
            line[17] = null;
            line[18] = null;
            line[19] = null;
            line[20] = null;
            line[21] = null;
            line[22] = null;
            line[23] = "1";
            line[24] = "1";
            line[25] = null;
            line[26] = null;
            line[27] = null;
            line[28] = null;
            line[29] = product.getDescription();
            line[30] = product.getDescription();
            line[31] = product.getTitle().replaceAll(" ", ",");
            line[32] = product.getTitle();
            line[33] = product.getDescription();
            line[34] = null;
            line[35] = null;
            line[36] = null;
            line[37] = "1";
            line[38] = "13/03/2016";
            line[39] = "13/03/2016";
            line[40] = "1";
            line[41] = null;
            line[42] = "0";
            line[43] = null;
            line[44] = "1";
            line[45] = "new";
            line[46] = "1";
            line[47] = "0";
            line[48] = "0";
            line[49] = "0";
            line[50] = "0";
            line[51] = "0";
            line[52] = "0";
            line[53] = "0";
            writer.writeNext(line);
        }
        int index = 0;
        for (Category category : categories) {
            String[] lineCat = category.toCsv();
            lineCat[0] = String.valueOf(index);
            writerCat.writeNext(lineCat);
            index++;
        }
        writer.close();
        writerCat.close();
    }

    private void fillCategorie(List<Category> categoryList, List<String> categories) {
        for (int i = 0; i < categories.size(); i++) {
            Category category = new Category();
            category.setActive("1");
            category.setName(categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase());
            if (i != 0) {
                category.setParent(categories.get(i - 1).substring(0, 1).toUpperCase() + categories.get(i - 1).substring(1).toLowerCase());
            } else {
                category.setParent(null);
            }
            category.setRoot("0");
            category.setDescription(categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase());
            category.setMeta_title(categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase());
            category.setMeta_keywords(categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase());
            category.setMeta_description(categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase());
            category.setRewritten(categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase());
            category.setImage(null);
            if (!categoryList.contains(category)) {
                categoryList.add(category);
            }
        }
    }

    private String concatString(List<String> categories) {
        String result = "";
        for (int i = 0; i < categories.size(); i++) {
            if (!categories.get(i).equalsIgnoreCase("Vrac")) {
                if (result.contains("," + categories.get(i))) {
                    if (i == categories.size() - 1) {
                        result = result.substring(0, result.length() - 1);
                    }
                } else {
                    result += categories.get(i).substring(0, 1).toUpperCase() + categories.get(i).substring(1).toLowerCase();
                    if (i != categories.size() - 1) {
                        result += ",";
                    }
                }
            } else {
                if (i == categories.size() - 1) {
                    result = result.substring(0, result.length() - 1);
                }
            }
        }
        return result;
    }

    //	@Test
    //	public void userUrlTest() throws IOException {
    //		File file = new File(getClass().getClassLoader().getResource("fait_maison_url.html").getFile());
    //		BufferedReader buffReader = new BufferedReader(new FileReader(file));
    //		StringBuilder result = new StringBuilder("");
    //
    //		while (buffReader.ready()) {
    //			String read = buffReader.readLine();
    //			result.append(read).append("\n");
    //		}
    //		buffReader.close();
    //		String url = faitMaisonReader.getUserUrl(result.toString());
    //		Assert.assertTrue(url.equals("http://www.fait-maison.com/member/shops/8282"));
    //	}

}
