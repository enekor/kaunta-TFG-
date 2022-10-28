import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/utils/api_call.dart';
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

  void cambiarTema() {
    if (Temas().actual.value == 1) {
      Temas().actual.value = 0;
    } else {
      Temas().actual.value = 1;
    }
  }
}

Future<void> openLoginMenu(BuildContext context) async {
  RxBool isLogin = true.obs;
  RxBool valido = true.obs;
  String usuario = "";
  String pass = "";
  String usuarioReg = "";
  String pass1 = "";
  String pass2 = "";
  RxBool nombreValido = true.obs;
  RxBool passValido = true.obs;
  RxString mensajeReg = "Registrarse".obs;
  RxString mensajeLog = "Login".obs;

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
          return FutureBuilder(
            future: ApiCall().me(),
            builder: (context, snapshot) {
              if (snapshot.hasData) {
                if (snapshot.data as bool) {
                  abrirPagina(const Home(), context);
                  Navigator.of(context).pop();
                  return Container();
                } else {
                  return Obx(
                    () => ApiCall().codigo == 200
                        ? isLogin.value
                            ? login(
                                () {
                                  ApiCall().login(usuario, pass);
                                  if (ApiCall().codigo != 200) {
                                    mensajeLog.value =
                                        "Usuario o contraseña incorrectos";
                                  } else {
                                    mensajeLog.value = "Login";
                                    abrirPagina(const Home(), context);
                                    Navigator.of(context).pop();
                                  }
                                },
                                (a) {
                                  usuario = a;
                                },
                                (a) {
                                  pass = a;
                                },
                                valido,
                                () => isLogin.value = false,
                                mensajeLog.value,
                              )
                            : register(() {
                                if (pass1 == pass2) {
                                  passValido.value = true;

                                  ApiCall().register(usuarioReg, pass1);
                                  if (ApiCall().codigo == 200) {
                                    mensajeReg.value = "Registrase";
                                    abrirPagina(const Home(), context);
                                    Navigator.of(context).pop();
                                  } else if (ApiCall().codigo == 409) {
                                    mensajeLog.value =
                                        "El nombre de usuario ya existe";
                                  } else {
                                    mensajeLog.value =
                                        "Error al registrar usuario";
                                  }
                                } else {
                                  passValido.value = false;
                                  mensajeReg.value =
                                      "Las contraseñas no coinciden";
                                }
                              },
                                () => isLogin.value = true,
                                (a) => usuarioReg = a,
                                (a) => pass1 = a,
                                (a) => pass2 = a,
                                passValido.value,
                                nombreValido.value,
                                mensajeReg.value)
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
                                    size: 45,
                                  ),
                                  const SizedBox(height: 25),
                                  Text(
                                    "No se ha podido conectar, prueba de nuevo mas tarde o conectate de forma offline",
                                    style: TextStyle(
                                      color: Temas().getTextColor(),
                                      fontSize: 25,
                                    ),
                                  ),
                                  const SizedBox(height: 25),
                                  ElevatedButton(
                                    onPressed: () =>
                                        Navigator.of(context).pop(),
                                    child: Text(
                                      "Aceptar",
                                      style: TextStyle(
                                          color: Temas().getButtonTextColor()),
                                    ),
                                  ),
                                ],
                              ),
                            ),
                          ),
                  );
                }
              } else {
                return Obx(
                  () => Center(
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
                      ),
                    ),
                  ),
                );
              }
            },
          );
        } else {
          return Obx(
            () => Center(
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
                ),
              ),
            ),
          );
        }
      },
    ),
  );
}

void onClick(BuildContext context) {
  var snack = Snacker().simpleSnack("hola", Colors.blue, const Icon(Icons.abc));

  showSnack(snack, context);
}

void abrirPagina(Widget pagina, BuildContext context) {
  Navigator.of(context).push(
    MaterialPageRoute(
      builder: (context) => pagina,
    ),
  );
}
