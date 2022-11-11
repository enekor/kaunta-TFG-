import 'package:flutter/material.dart';
import 'package:flutter/services.dart';
import 'package:get/get.dart';
import 'package:kaunta/home/globales.dart';
import 'package:kaunta/home/home.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/utils/shared_preferences.dart';
import 'package:kaunta/widgets/snackers.dart';
import 'package:kaunta/widgets/widgets.dart';

class Botones extends StatelessWidget {
  const Botones({super.key});

  @override
  Widget build(BuildContext context) {
    return WillPopScope(
      onWillPop: () async {
        SystemChannels.platform.invokeMethod('SystemNavigator.pop');
        return false;
      },
      child: Obx(
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
                        onPressed: () {
                          abrirPagina(const Home(), context);
                          Globales().conectado = false;
                        },
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
  Navigator.pop(context);
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
    elevation: 100,
    barrierColor: const Color.fromARGB(67, 1, 38, 40),
    backgroundColor: Temas().getBackground(),
    context: context,
    builder: (context) => FutureBuilder(
      future: ApiCall().testConnection(),
      builder: (context, snapshot) {
        if (snapshot.hasData) {
          return FutureBuilder(
              future: ApiCall().me(),
              builder: (context, snapshot) {
                if (snapshot.hasData) {
                  if (snapshot.data != 200) {
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
                                        return Center(
                                          child: Column(
                                              mainAxisAlignment:
                                                  MainAxisAlignment.spaceEvenly,
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.center,
                                              children: [
                                                Text(
                                                  "Conectado",
                                                  style: TextStyle(
                                                      color: Temas()
                                                          .getTextColor(),
                                                      fontSize: 25),
                                                ),
                                                Row(
                                                  mainAxisAlignment:
                                                      MainAxisAlignment
                                                          .spaceEvenly,
                                                  crossAxisAlignment:
                                                      CrossAxisAlignment.center,
                                                  children: [
                                                    ElevatedButton(
                                                      style: ElevatedButton
                                                          .styleFrom(
                                                        backgroundColor: Temas()
                                                            .getPrimary(),
                                                      ),
                                                      onPressed: () {
                                                        abrirPagina(
                                                            const Home(),
                                                            context);
                                                      },
                                                      child: const Text("Ok"),
                                                    ),
                                                    ElevatedButton(
                                                      style: ElevatedButton
                                                          .styleFrom(
                                                        backgroundColor: Temas()
                                                            .getPrimary(),
                                                      ),
                                                      onPressed: () {
                                                        SharedPreferencesEditor()
                                                            .postSharedPreferences(
                                                                "token",
                                                                "",
                                                                "String");
                                                        Navigator.pop(context);
                                                      },
                                                      child:
                                                          const Text("Cancel"),
                                                    ),
                                                  ],
                                                )
                                              ]),
                                        );
                                      } else {
                                        return Center(
                                          child: Column(
                                            mainAxisAlignment:
                                                MainAxisAlignment.center,
                                            children: [
                                              Text(
                                                "No logeado",
                                                style: TextStyle(
                                                  color: Temas().getSecondary(),
                                                  fontSize: 35,
                                                ),
                                              ),
                                              const SizedBox(height: 25),
                                              ElevatedButton(
                                                style: ElevatedButton.styleFrom(
                                                  backgroundColor:
                                                      Temas().getPrimary(),
                                                ),
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
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                            CircularProgressIndicator(
                                              color: Temas().getPrimary(),
                                            ),
                                            const Text("Iniciando sesion..."),
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
                                      regText.value =
                                          "Las contraseñas no coinciden";
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
                                      if (snapshot.data == 202) {
                                        return Center(
                                          child: Column(
                                              mainAxisAlignment:
                                                  MainAxisAlignment.spaceEvenly,
                                              crossAxisAlignment:
                                                  CrossAxisAlignment.center,
                                              children: [
                                                Text(
                                                  "Registrado",
                                                  style: TextStyle(
                                                      color: Temas()
                                                          .getTextColor(),
                                                      fontSize: 25),
                                                ),
                                                ElevatedButton(
                                                  style:
                                                      ElevatedButton.styleFrom(
                                                    backgroundColor:
                                                        Temas().getPrimary(),
                                                  ),
                                                  onPressed: () {
                                                    Globales().conectado = true;
                                                    abrirPagina(
                                                        const Home(), context);
                                                  },
                                                  child: const Text("Ok"),
                                                ),
                                              ]),
                                        );
                                      } else {
                                        return Center(
                                          child: Column(
                                            mainAxisAlignment:
                                                MainAxisAlignment.center,
                                            children: [
                                              Text(
                                                "No se pudo registrar",
                                                style: TextStyle(
                                                  color: Temas().getSecondary(),
                                                  fontSize: 35,
                                                ),
                                              ),
                                              const SizedBox(height: 25),
                                              ElevatedButton(
                                                style: ElevatedButton.styleFrom(
                                                  backgroundColor:
                                                      Temas().getPrimary(),
                                                ),
                                                onPressed: () {
                                                  loginPressed.value = true;
                                                  Navigator.pop(context);
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
                                          mainAxisAlignment:
                                              MainAxisAlignment.center,
                                          children: [
                                            CircularProgressIndicator(
                                              color: Temas().getPrimary(),
                                            ),
                                            const Text("Registrando..."),
                                          ],
                                        ),
                                      );
                                    }
                                  },
                                ),
                    );
                  } else {
                    return Column(
                        mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                        crossAxisAlignment: CrossAxisAlignment.center,
                        children: [
                          Text(
                            "Conectado",
                            style: TextStyle(
                                color: Temas().getTextColor(), fontSize: 25),
                          ),
                          Row(
                            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                            crossAxisAlignment: CrossAxisAlignment.center,
                            children: [
                              ElevatedButton(
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Temas().getPrimary(),
                                ),
                                onPressed: () {
                                  Globales().conectado = true;
                                  abrirPagina(const Home(), context);
                                },
                                child: const Text("Ok"),
                              ),
                              ElevatedButton(
                                style: ElevatedButton.styleFrom(
                                  backgroundColor: Temas().getPrimary(),
                                ),
                                onPressed: () {
                                  SharedPreferencesEditor()
                                      .postSharedPreferences(
                                          "token", "", "String");
                                  Navigator.pop(context);
                                },
                                child: const Text("Cancel"),
                              ),
                            ],
                          )
                        ]);
                  }
                } else {
                  return Center(
                    child:
                        CircularProgressIndicator(color: Temas().getPrimary()),
                  );
                }
              });
        } else {
          return Center(
            child: Column(
              mainAxisAlignment: MainAxisAlignment.center,
              crossAxisAlignment: CrossAxisAlignment.center,
              children: [
                CircularProgressIndicator(
                  color: Temas().getPrimary(),
                ),
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
    RegisterWidget(
      onTap,
      onTapLogin,
      onChangeUser,
      onChangePass1,
      onChanegPass2,
      passValido,
      texto,
    );
