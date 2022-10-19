import 'package:flutter/material.dart';
import 'package:get/get.dart';
import 'package:kaunta/json.dart';
import 'package:kaunta/model/modelo.dart';
import 'package:kaunta/paginas/contadores/ver_contador.dart';
import 'package:kaunta/paginas/listado/listado.dart';
import 'package:kaunta/themes/temas.dart';
import 'package:kaunta/widgets/snackers.dart';

Widget cTextField(
        dynamic onChange, String label, IconData icono, RxBool valido) =>
    TextField(
      style: TextStyle(color: Temas().getTextColor()),
      decoration: InputDecoration(
        labelStyle: TextStyle(color: Temas().getTextColor()),
        suffixIcon: Icon(icono, color: cambiarColor(valido.value)),
        labelText: label,
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(16.0),
          borderSide: BorderSide(color: cambiarColor(valido.value)),
        ),
        focusedBorder: OutlineInputBorder(
          borderSide: BorderSide(color: cambiarColor(valido.value)),
        ),
      ),
      onChanged: onChange,
    );

Widget cPasswordField(
        dynamic onChange, String label, IconData icono, bool valido) =>
    TextField(
      obscureText: true,
      autocorrect: false,
      enableSuggestions: false,
      style: TextStyle(color: Temas().getTextColor()),
      decoration: InputDecoration(
        labelStyle: TextStyle(color: Temas().getTextColor()),
        suffixIcon: Icon(icono, color: cambiarColor(valido)),
        labelText: label,
        enabledBorder: OutlineInputBorder(
          borderRadius: BorderRadius.circular(16.0),
          borderSide: BorderSide(color: cambiarColor(valido)),
        ),
        focusedBorder: OutlineInputBorder(
          borderSide: BorderSide(color: cambiarColor(valido)),
        ),
      ),
      onChanged: onChange,
    );

Widget cGroupListItem(Grupo g, int index, BuildContext context) => Obx(
      () => Card(
        color: Temas().getSecondary(),
        elevation: 10.0,
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
        child: Padding(
          padding: const EdgeInsets.all(25.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Text(
                g.nombre!.value,
                style: TextStyle(color: Temas().getTextColor()),
              ),
              Text(g.counters!.length.toString(),
                  style: TextStyle(color: Temas().getTextColor())),
              IconButton(
                icon: const Icon(Icons.delete),
                color: Colors.redAccent,
                onPressed: () {
                  var snack = Snacker().confirmSnack(
                    g.nombre!.value,
                    context,
                    borrarGrupo,
                    g,
                    true,
                    "Borrar",
                  );

                  showSnack(snack, context);
                },
              )
            ],
          ),
        ),
      ),
    );

Widget cCardItemContador(Contador c, int index, BuildContext context) => Obx(
      () => GestureDetector(
        onTap: () {
          Listado().cActual = c;

          Navigator.of(context).push(
            MaterialPageRoute(
              builder: (context) => const VerContador(),
            ),
          );
        },
        child: Card(
          color: Temas().getSecondary(),
          shape:
              RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
          child: Padding(
            padding: const EdgeInsets.all(15),
            child: Row(
              children: [
                Expanded(
                  flex: 2,
                  child: Image.network(
                    c.image!.value,
                  ),
                ),
                Expanded(
                  flex: 6,
                  child: Center(
                    child: Column(
                      children: [
                        Text(
                          c.name!.value,
                          style: TextStyle(
                            color: Temas().getTextColor(),
                            fontSize: 30,
                          ),
                        ),
                        Text(
                          c.count!.value.toString(),
                          style: TextStyle(
                            color: Temas().getTextColor(),
                            fontSize: 25,
                          ),
                        ),
                        Row(
                          mainAxisAlignment: MainAxisAlignment.spaceEvenly,
                          children: [
                            IconButton(
                              onPressed: () => c.count!.value += 1,
                              icon: const Icon(
                                Icons.add_circle_outline_rounded,
                                color: Colors.greenAccent,
                              ),
                              iconSize: 50,
                            ),
                            IconButton(
                              onPressed: () => c.count!.value -= 1,
                              icon: const Icon(
                                Icons.remove_circle_outline_rounded,
                                color: Colors.redAccent,
                              ),
                              iconSize: 50,
                            ),
                          ],
                        ),
                      ],
                    ),
                  ),
                ),
                Expanded(
                  flex: 2,
                  child: IconButton(
                    onPressed: () {
                      var snack = Snacker().confirmSnack(
                        c.name!.value,
                        context,
                        borrarGrupo,
                        c,
                        false,
                        "Borrar",
                      );

                      showSnack(snack, context);
                    },
                    icon: const Icon(
                      Icons.delete_outline_rounded,
                      color: Colors.deepOrangeAccent,
                    ),
                    iconSize: 40,
                  ),
                ),
              ],
            ),
          ),
        ),
      ),
    );

Widget cRestoreGroupCardItem(Grupo g, BuildContext context) => Obx(
      () => Card(
        color: Temas().getSecondary(),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
        child: Padding(
          padding: const EdgeInsets.all(25.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Expanded(
                flex: 8,
                child: Center(
                  child: Text(g.nombre!.value,
                      style: TextStyle(color: Temas().getTextColor())),
                ),
              ),
              Expanded(
                flex: 2,
                child: IconButton(
                  onPressed: () {
                    var snack = Snacker().confirmSnack(
                      g.nombre!.value,
                      context,
                      restaurarGrupo,
                      g,
                      true,
                      "Restaurar",
                    );

                    showSnack(snack, context);
                  },
                  icon: const Icon(Icons.restore_from_trash_rounded),
                  color: Colors.greenAccent,
                ),
              )
            ],
          ),
        ),
      ),
    );

Widget cRestoreConterCardItem(Contador c, BuildContext context) => Obx(
      () => Card(
        color: Temas().getSecondary(),
        shape: RoundedRectangleBorder(borderRadius: BorderRadius.circular(15)),
        child: Padding(
          padding: const EdgeInsets.all(25.0),
          child: Row(
            mainAxisAlignment: MainAxisAlignment.spaceEvenly,
            children: [
              Expanded(
                flex: 8,
                child: Center(
                  child: Text(c.name!.value,
                      style: TextStyle(color: Temas().getTextColor())),
                ),
              ),
              Expanded(
                flex: 2,
                child: IconButton(
                  onPressed: () {
                    var snack = Snacker().confirmSnack(
                      c.name!.value,
                      context,
                      restaurarGrupo,
                      c,
                      false,
                      "Restaurar",
                    );
                    showSnack(snack, context);
                  },
                  icon: const Icon(Icons.restore_from_trash_rounded),
                  color: Colors.greenAccent,
                ),
              ),
            ],
          ),
        ),
      ),
    );

void borrarGrupo(Object g, bool isGrupo) {
  if (isGrupo) {
    (g as Grupo).activo!.value = false;
  } else {
    (g as Contador).active!.value = false;
  }

  saveCounters();
  loadCounters();
}

void restaurarGrupo(Object g, bool isGrupo) {
  if (isGrupo) {
    (g as Grupo).activo!.value = true;
  } else {
    (g as Contador).active!.value = true;
  }

  saveCounters();
  loadCounters();
}

Widget login(dynamic onTap, dynamic onChangeUser, dynamic onChangePass,
        RxBool valido, dynamic onRegisterTap) =>
    Obx(
      () => Padding(
        padding: const EdgeInsets.all(60.0),
        child: Center(
          child: Column(
            mainAxisAlignment: MainAxisAlignment.center,
            crossAxisAlignment: CrossAxisAlignment.center,
            children: [
              Text(
                "Login",
                style: TextStyle(color: Temas().getPrimary(), fontSize: 35),
              ),
              const SizedBox(height: 30),
              cTextField(onChangeUser, "Usuario", Icons.person_rounded, valido),
              const SizedBox(height: 30),
              cPasswordField(onChangePass, "Contraseña", Icons.password_rounded,
                  valido.value),
              const SizedBox(
                height: 80,
              ),
              ElevatedButton(onPressed: onTap, child: const Text("Guardar")),
              TextButton(
                  onPressed: onRegisterTap, child: const Text("Registrarse")),
            ],
          ),
        ),
      ),
    );

Widget register(
        dynamic onTap,
        dynamic onTapLogin,
        dynamic onChangeUser,
        dynamic onChangePass1,
        dynamic onChangePass2,
        bool passValido,
        bool nombreValido,
        String mensaje) =>
    Padding(
      padding: const EdgeInsets.all(60),
      child: Column(
        mainAxisAlignment: MainAxisAlignment.center,
        crossAxisAlignment: CrossAxisAlignment.center,
        children: [
          Text(
            mensaje,
            style: TextStyle(
              color: Temas().getPrimary(),
              fontSize: 35,
            ),
          ),
          const SizedBox(height: 30),
          cTextField(
            onChangeUser,
            "Nombre de usuario",
            Icons.person,
            true.obs,
          ),
          const SizedBox(height: 30),
          cPasswordField(
            onChangePass1,
            "Contraseña",
            Icons.password_rounded,
            passValido,
          ),
          const SizedBox(height: 30),
          cPasswordField(
            onChangePass2,
            "Repita la contraseña",
            Icons.password_rounded,
            passValido,
          ),
          const SizedBox(height: 50),
          ElevatedButton(
            onPressed: onTap,
            child: Text(
              "Registrarse",
              style: TextStyle(color: Temas().getButtonTextColor()),
            ),
          ),
          TextButton(
            onPressed: onTapLogin,
            child: const Text("Login"),
          ),
        ],
      ),
    );
