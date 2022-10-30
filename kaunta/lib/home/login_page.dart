import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/utils/api_call.dart';
import 'package:kaunta/widgets/widgets.dart';

class LoginPage extends StatelessWidget {
  const LoginPage({super.key});

  @override
  Widget build(BuildContext context) {
    return Container();
  }
}

Future<Widget> loginPage() async {
  RxBool isLogin = true.obs;
  RxBool loginValido = true.obs;
  RxBool registerValido = true.obs;

  String userLogin = "";
  String passLogin = "";
  String userReg = "";
  String pass1Reg = "";
  String pass2Reg = "";
  RxString loginTxt = "Login".obs;
  RxString registerTxt = "Registrarse".obs;

  return isLogin.value == true
      ? login(
          loginValido.value = await loginFun(userLogin, passLogin,
              () => loginTxt.value = "Usuario o contraseña incorrectos"),
          (user) => userLogin = user,
          (pass) => passLogin = pass,
          loginValido,
          isLogin.value = false,
          loginTxt.value)
      : register(
          loginValido.value = await registrar(
            userReg,
            pass1Reg,
            pass2Reg,
            () => registerTxt.value = "Ya existe usuario con ese nombre",
            () => registerTxt.value = "Las contraseñas no coinciden",
          ),
          isLogin.value = true,
          (user) => userReg = user,
          (pass) => pass1Reg = pass,
          (pass) => pass2Reg = pass,
          loginValido.value,
          registerTxt.value);
}

Future<bool> registrar(String userReg, String pass1Reg, String pass2Reg,
    nombreYaExiste, passNoCoinciden) async {
  bool reg = false;

  if (pass1Reg == pass2Reg) {
    reg = await ApiCall().register(userReg, pass1Reg);
  } else {
    passNoCoinciden;
  }

  if (!reg) {
    if (ApiCall().codigo == 409) {
      nombreYaExiste;
    }
  }

  return reg;
}

Future<bool> loginFun(String user, String pass, loginIcorrecto) async {
  bool ret;

  ret = await ApiCall().login(user, pass);

  if (ret == false) {
    loginIcorrecto;
  }

  return ret;
}
