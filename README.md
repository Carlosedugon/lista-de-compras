# Lista de Compras — projeto Android

App Android que roda a página da lista de compras dentro de um WebView.
Sem bibliotecas externas: só o WebView do próprio sistema, então o build é rápido
e não depende de dependências que podem quebrar.

- Pacote: `br.com.listadecompras`
- Android mínimo: 8.0 (API 26)
- A lista fica salva no aparelho (localStorage do WebView)

## Gerar o APK

1. Instale o [Android Studio](https://developer.android.com/studio) (a versão para Mac com Apple Silicon, se for o caso).
2. Abra o Android Studio → **Open** → selecione esta pasta (`lista-de-compras-android`).
3. Espere o Gradle sincronizar. Na primeira vez ele baixa o Gradle 8.7 e o SDK do Android — leva alguns minutos.
4. Menu **Build → Build Bundle(s) / APK(s) → Build APK(s)**.
5. Quando terminar, clique em **locate** na notificação. O arquivo estará em:

```
app/build/outputs/apk/debug/app-debug.apk
```

Esse APK de debug já instala e funciona. Para instalar no celular, transfira o
arquivo e permita "instalar de fontes desconhecidas" no Android.

## Instalar direto pelo cabo

Com o celular conectado e a depuração USB ativada, basta apertar o ▶ (Run) no
Android Studio — ele compila e instala em um passo só.

## Versão de release (opcional)

Para um APK assinado, use **Build → Generate Signed Bundle / APK → APK**, crie um
keystore e guarde-o: ele é necessário para publicar atualizações do mesmo app.

## Editar a página

Todo o app é o arquivo `app/src/main/assets/index.html` — HTML, CSS e JS num
arquivo só. Alterou, recompila e pronto.

O bloco de script no topo do arquivo é o único ajuste em relação à versão web:
ele reimplementa `window.storage` usando o `localStorage` do WebView.

## Fontes

As fontes (Bricolage Grotesque e IBM Plex) vêm do Google Fonts pela internet. Sem
conexão, o app funciona normalmente e cai nas fontes do sistema. Se quiser que
fiquem sempre iguais, baixe os `.woff2`, coloque em `assets/fonts/` e troque o
`<link>` por um `@font-face` apontando para os arquivos locais.

---

## Gerar o APK pelo GitHub (sem Android Studio)

O projeto já vem com `.github/workflows/android.yml`. Basta subir o repositório:

```bash
cd lista-de-compras-android
git init
git add .
git commit -m "Lista de compras"
git branch -M main
git remote add origin https://github.com/SEU_USUARIO/lista-de-compras.git
git push -u origin main
```

A cada push o GitHub compila o app numa máquina Linux dele. Acompanhe na aba
**Actions** do repositório — leva uns 3 a 5 minutos. No fim da execução, em
**Artifacts**, aparece `lista-de-compras-apk` para baixar (vem num `.zip`, com o
`app-debug.apk` dentro).

Também dá para disparar a build na mão em **Actions → Gerar APK → Run workflow**,
sem precisar fazer um commit novo.

### Link direto para baixar no celular

Artefato exige estar logado no GitHub e vem compactado, o que atrapalha no
telefone. Marcando uma versão com tag, o workflow publica o APK numa Release, que
dá um link limpo de download:

```bash
git tag v1.0
git push origin v1.0
```

Depois é só abrir a aba **Releases** do repositório pelo navegador do celular e
baixar o `app-debug.apk`.

Repositório privado também funciona: contas gratuitas têm 2.000 minutos de
Actions por mês, e cada build dessas gasta cerca de 4.

### Sobre a assinatura

O APK sai assinado com a chave de debug, que o próprio Gradle gera. Instala e roda
normalmente no seu aparelho, mas não serve para publicar na Play Store — para isso
seria preciso gerar um keystore e guardá-lo nos Secrets do repositório.
