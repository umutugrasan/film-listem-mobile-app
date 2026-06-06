# Film Listem

Mobil Programlama dersi dönem sonu ödevi. Kullanıcının film/dizi izleme listesini tuttuğu basit bir Android uygulamasıdır.

## Özellikler

- Film/dizi ara (OMDb API ile)
- Listeme ekle / listemden çıkar
- Hangi filmi izledim işaretle
- Tüm liste bulut veritabanında (Firebase Firestore) tutulur

## Kullanılan Teknolojiler

- Dil: Java
- 3 farklı Activity: `MainActivity` (Listem), `SearchActivity` (Arama), `DetailActivity` (Detay)
- Veritabanı: Firebase Firestore (bulut)
- Dış servis: OMDb API
- UI: Material Components, RecyclerView, CardView
- Görsel yükleme: Glide
- Ağ kütüphanesi: Retrofit + Gson

## Kurulum

### 1) OMDb API anahtarı al
- [https://www.omdbapi.com/apikey.aspx](https://www.omdbapi.com/apikey.aspx) adresinden ücretsiz key al.
- `app/src/main/java/com/example/odev/Constants.java` dosyasını aç ve `OMDB_API_KEY` değerini kendi anahtarınla değiştir.

### 2) Firebase projesi bağla
- [https://console.firebase.google.com](https://console.firebase.google.com) adresinden yeni bir proje aç.
- Android uygulama ekle, package adı: `com.example.odev`
- `google-services.json` dosyasını indir ve `app/` klasörüne yerleştir.
- Firestore Database'i Test modunda başlat. Collection adı: `movies` (kod otomatik kullanır).

### 3) Çalıştır
- Android Studio'da projeyi aç.
- Gradle sync.
- Emülatör veya cihazda çalıştır.

## Ekran Yapısı

1. **Ana Ekran (Listem):** Eklediğin filmler RecyclerView'da listelenir. Sağ alttaki + butonu yeni film aramaya götürür.
2. **Arama Ekranı:** OMDb API'de film/dizi arar, sonuçları listeler.
3. **Detay Ekranı:** Filmin detayını gösterir. "Listeme Ekle / Çıkar" ve "İzledim" işaretleme yapabilirsin.

## Notlar

- `google-services.json` ve API anahtarı .gitignore içindedir, repo'ya yüklenmez.
- Min SDK: 24 (Android 7.0).
