import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/api_call/api_call.dart';
import 'package:kaunta/home/home.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/snackers.dart';
import 'package:kaunta/widgets/widgets.dart';

class Botones extends StatelessWidget {
  const Botones({super.key});

  @override
  Widget build(BuildContext context) {
    return Obx(
      () => Scaffold(
        backgroundColor: Temas().getBackground(),
        floatingActionButton: FloatingActionButton(
          onPressed: () => cambiarTema(),
          child: Icon(Temas().actual.value == 0
              ? Icons.dark_mode_rounded
              : Icons.light_mode_rounded),
        ),
        body: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            children: [
              Padding(
                padding: const EdgeInsets.only(bottom: 50),
                child: Column(
                  children: [
                    IconButton(
                      onPressed: () => abrirPagina(const Home(), context),
                      icon: const Icon(
                        Icons.wifi_off_rounded,
                        color: Colors.redAccent,
                      ),
                    ),
                    const Text(
                      "Sin conexion",
                      style: TextStyle(color: Colors.redAccent),
                    )
                  ],
                ),
              ),
              Column(
                children: [
                  IconButton(
                    onPressed: () => openLoginMenu(context),
                    icon: const Icon(
                      Icons.wifi_rounded,
                      color: Colors.greenAccent,
                    ),
                  ),
                  const Text(
                    "Conectado",
                    style: TextStyle(color: Colors.greenAccent),
                  ),
                ],
              ),
            ],
          ),
        ),
      ),
    );
  }

  void abrirPagina(Widget pagina, BuildContext context) {
    Navigator.of(context).push(
      MaterialPageRoute(
        builder: (context) => pagina,
      ),
    );
  }

  void cambiarTema() {
    if (Temas().actual.value == 1) {
      Temas().actual.value = 0;
    } else {
      Temas().actual.value = 1;
    }
  }
}

Future<void> openLoginMenu(BuildContext context) async {
  RxBool valido = true.obs;

  showModalBottomSheet(
    context: context,
    shape: const RoundedRectangleBorder(
      borderRadius: BorderRadius.only(
          topLeft: Radius.circular(25), topRight: Radius.circular(25)),
    ),
    backgroundColor: Temas().getBackground(),
    builder: (BuildContext bc) => FutureBuilder(
      future: ApiCall().testConnection(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return ApiCall().codigo == 200
              ? login(
                  () {},
                  (a) {},
                  (a) {},
                  valido,
                )
              : Center(
                  child: Padding(
                    padding: const EdgeInsets.all(25.0),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        Icon(
                          Icons.wifi_off_rounded,
                          color: Temas().getTextColor(),
                        ),
                        Text(
                          "No se ha podido conectar, prueba de nuevo mas tarde o conectate de forma offline",
                          style: TextStyle(
                            color: Temas().getTextColor(),
                          ),
                        ),
                        const SizedBox(height: 25),
                        ElevatedButton(
                          onPressed: () => Navigator.of(context).pop(),
                          child: Text(
                            "Aceptar",
                            style:
                                TextStyle(color: Temas().getButtonTextColor()),
                          ),
                        ),
                      ],
                    ),
                  ),
                );
        } else {
          return Obx(() => Center(
                child: Container(
                    padding: const EdgeInsets.all(100),
                    color: Temas().getBackground(),
                    child: Column(
                      mainAxisAlignment: MainAxisAlignment.center,
                      crossAxisAlignment: CrossAxisAlignment.center,
                      children: [
                        const CircularProgressIndicator(),
                        const SizedBox(height: 25),
                        Text(
                          "Estableciendo conexión con la red...",
                          style: TextStyle(
                            color: Temas().getTextColor(),
                          ),
                        ),
                      ],
                    )),
              ));
        }
      },
    ),
  );
}

void onClick(BuildContext context) {
  var snack = Snacker().simpleSnack("hola", Colors.blue, const Icon(Icons.abc));

  showSnack(snack, context);
}
