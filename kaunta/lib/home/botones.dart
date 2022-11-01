import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/home.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/utils/api_call.dart';
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
                    onPressed: () => loginRequest(context),
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

void loginRequest(BuildContext context) {
  RxBool isLogin = true.obs;
  RxBool loginPressed = false.obs;
  RxBool registerPressed = false.obs;
  RxBool loginValido = true.obs;
  RxBool regValido = true.obs;
  String userLogin = "";
  String passLogin = "";
  String userReg = "";
  String pass1Reg = "";
  String pass2Reg = "";
  RxString loginText = "Login".obs;
  RxString regText = "Register".obs;

  showModalBottomSheet(
    shape: RoundedRectangleBorder(
      borderRadius: BorderRadius.circular(20.0),
    ),
    context: context,
    builder: (context) => FutureBuilder(
      future: ApiCall().testConnection(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return Obx(
            () => isLogin.value
                ? loginPressed.value == false
                    ? login(
                        () {
                          if (userLogin != "" && passLogin != "") {
                            ApiCall().usuarioLogin = userLogin;
                            ApiCall().passLogin = passLogin;
                            loginPressed.value = true;
                          } else {
                            loginValido.value = false;
                            loginText.value =
                                "Rellene los campos correctamente";
                          }
                        },
                        (user) => userLogin = user,
                        (pass) => passLogin = pass,
                        loginValido,
                        () => isLogin.value = false,
                        loginText.value,
                      )
                    : FutureBuilder(
                        future: ApiCall().login(),
                        builder: (context, snapshot) {
                          if (snapshot.hasData) {
                            if ((snapshot.data as int) == 200) {
                              abrirPagina(const Home(), context);
                              //Navigator.pop(context);
                              return const Center(
                                child: Text("Logeado"),
                              );
                            } else {
                              return Center(
                                child: Column(
                                  children: [
                                    const Text("No logeado"),
                                    ElevatedButton(
                                      onPressed: () {
                                        Navigator.pop(context);
                                        loginPressed.value = true;
                                      },
                                      child: const Text("Ok"),
                                    ),
                                  ],
                                ),
                              );
                            }
                          } else {
                            return Center(
                              child: Column(
                                children: const [
                                  CircularProgressIndicator(),
                                  Text("Iniciando sesion..."),
                                ],
                              ),
                            );
                          }
                        },
                      )
                : registerPressed.value == false
                    ? RegisterWidget(
                        () {
                          if (pass1Reg == pass2Reg) {
                            ApiCall().passReg = pass1Reg;
                            ApiCall().usuarioReg = userReg;
                            registerPressed.value = true;
                          } else {
                            regText.value = "Las contraseñas no coinciden";
                            regValido.value = false;
                          }
                        },
                        () => isLogin.value = true,
                        (user) => userReg = user,
                        (pass) => pass1Reg = pass,
                        (pass) => pass2Reg = pass,
                        regValido.value,
                        regText.value,
                      )
                    : FutureBuilder(
                        future: ApiCall().register(),
                        builder: (context, snapshot) {
                          if (snapshot.hasData) {
                            if ((snapshot.data as int) == 202) {
                              abrirPagina(const Home(), context);
                              //Navigator.pop(context);
                              return const Center(
                                child: Text("Registrado"),
                              );
                            } else {
                              return Center(
                                child: Column(
                                  children: [
                                    const Text("No se pudo registrar"),
                                    ElevatedButton(
                                      onPressed: () {
                                        Navigator.pop(context);
                                        loginPressed.value = true;
                                      },
                                      child: const Text("ok"),
                                    ),
                                  ],
                                ),
                              );
                            }
                          } else {
                            return Center(
                              child: Column(
                                children: const [
                                  CircularProgressIndicator(),
                                  Text("Registrando..."),
                                ],
                              ),
                            );
                          }
                        },
                      ),
          );
        } else {
          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                const CircularProgressIndicator(),
                Text(
                  "Conectando con la api...",
                  style: TextStyle(
                    color: Temas().getTextColor(),
                    fontSize: 23,
                  ),
                )
              ],
            ),
          );
        }
      },
    ),
  );
}

Widget login(onTap, onUserChange, onPassChange, loginValido, onRegTap, texto) =>
    Login(
      onTap,
      onUserChange,
      onPassChange,
      loginValido,
      onRegTap,
      texto,
    );

Widget register(onTap, onTapLogin, onChangeUser, onChangePass1, onChanegPass2,
        passValido, texto) =>
    RegisterWidget(onTap, onTapLogin, onChangeUser, onChangePass1,
        onChanegPass2, passValido, texto);
