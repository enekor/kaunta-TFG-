import 'package:get/get.dart';

class User {
  int? id;
  RxString? name;
  RxList<Grupo>? grupos;

  User({this.id, this.name, this.grupos});

  User.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'].obs;
    if (json['grupos'] != null) {
      grupos = <Grupo>[].obs;
      json['gupos'].forEach((v) {
        grupos!.add(Grupo.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name!.value;
    if (grupos != null) {
      data['grupos'] = grupos!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Grupo {
  int? id;
  RxString? nombre;
  RxBool? activo;
  RxList<Contador>? counters;

  Grupo({this.id, this.nombre, this.activo, this.counters});

  Grupo.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre = json['nombre'].obs;
    activo = json['activo'].obs;
    if (json['counters'] != null) {
      counters = <Contador>[].obs;
      json['counters'].forEach((v) {
        counters!.add(Contador.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['nombre'] = nombre!.value;
    data['activo'] = activo!.value;
    if (counters != null) {
      data['counters'] = counters!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Contador {
  int? id;
  RxString? name;
  RxString? descrition;
  RxString? image;
  RxInt? count;
  RxBool? active;

  Contador(
      {this.id,
      this.name,
      this.descrition,
      this.image,
      this.count,
      this.active});

  Contador.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name = json['name'].obs;
    descrition = json['descrition'].obs;
    image = json['image'].obs;
    count = json['count'].obs;
    active = json['active'].obs;
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = id;
    data['name'] = name!.value;
    data['descrition'] = descrition!.value;
    data['image'] = image!.value;
    data['count'] = count!.value;
    data['active'] = active!.value;
    return data;
  }
}
