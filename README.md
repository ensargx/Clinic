# Klinik Randevu Sistemi

Bu proje, Java dilinde geliştirilmiş bir **klinik randevu yönetim sistemidir**. Kullanıcılar, randevu alabilir, sistemdeki bilgileri görüntüleyebilir ve işlem yapabilir. Hem **grafik arayüz (UI)** hem de **komut satırı (CLI)** desteği sunar.

## ✨ Özellikler

- **Basit ve Kullanıcı Dostu Arayüz**: Kolay anlaşılır, sade bir UI tasarımı bulunmaktadır.
- **CLI Desteği**: Grafik arayüzün yanı sıra komut satırından da çalıştırılabilir.
- **Veri Kaydı**: Çalışma ortamı kaydedilip, daha sonra tekrar açılabilir.
- **Çok Dilli Destek**: Türkçe ve İngilizce dil seçenekleri sunar.

## 🛠️ Kurulum ve Çalıştırma

### Gereksinimler
- Java 11 veya üstü
- Bir IDE (IntelliJ IDEA, Eclipse vb.) veya terminal

### Çalıştırma (IntelliJ IDEA)

1. Proje dosyalarını indirin veya klonlayın:
   ```sh
   git clone https://github.com/ensargx/Clinic.git
   ```
2. IntelliJ IDEA'yı açın ve projeyi içe aktarın.
3. **File > Project Structure > Modules** üzerinden `src` klasörünü **Sources** olarak ayarlayın.
4. **Run > Edit Configurations** kısmından ana sınıfı (`org.clinic.Main`) seçin.
5. **Run** tuşuna basarak projeyi başlatın.

### Alternatif: Derlenmiş Sürümü Kullanma

1. **Releases** bölümünden en son sürümü indirin: [GitHub Releases](https://github.com/ensargx/Clinic/releases)
2. İndirilen `.jar` dosyasını terminalde çalıştırın:
   ```sh
   java -jar Clinic.jar
   ```

## 🔧 Kullanım

### UI Kullanımı
- **Hasta Kaydı:** Yeni hasta ekleyebilirsiniz.
- **Randevu Alma:** Doktor ve saat seçerek randevu oluşturabilirsiniz.
- **Randevu Görüntüleme:** Mevcut randevuları listeleyebilirsiniz.

### CLI Kullanımı
``java -jar Clinic.jar -no-gui``

## ✅ Lisans
Bu proje açık kaynak olup MIT lisansı ile dağıtılmaktadır.

---
