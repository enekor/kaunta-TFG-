import 'package:flutter/material.dart';
import 'package:get/get.dart';
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

void openLoginMenu(BuildContext context) {
  showModalBottomSheet(
    context: context,
    shape: const RoundedRectangleBorder(
      borderRadius: BorderRadius.only(
          topLeft: Radius.circular(25), topRight: Radius.circular(25)),
    ),
    backgroundColor: Temas().getBackground(),
    builder: (BuildContext bc) => Padding(
      padding: const EdgeInsets.all(25.0),
      child: Center(
        child: Column(
          mainAxisAlignment: MainAxisAlignment.center,
          crossAxisAlignment: CrossAxisAlignment.center,
          children: [
            const SizedBox(height: 100),
            cTextField((a) {}, "Usuario", Icons.person, -1),
            const SizedBox(height: 100),
            cTextField((a) {}, "Contraseña", Icons.password_rounded, -1),
            const SizedBox(height: 100),
            ElevatedButton(
                onPressed: () => onClick(context),
                child: const Text("Guardar")),
          ],
        ),
      ),
    ),
  );
}

void onClick(BuildContext context) {
  var snack = Snacker().simpleSnack("hola", Colors.blue, Icon(Icons.abc));

  showSnack(snack, context);
}
