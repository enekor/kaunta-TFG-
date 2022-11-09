import 'package:get/get.dart';

class User {
  int? id;
  RxString? name = "".obs;
  RxList<Grupo>? grupos = <Grupo>[].obs;

  User({this.id, this.name, this.grupos});

  User.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name!.value = json['username'];
    if (json['grupos'] != null || (json['grupos'] as List).isNotEmpty) {
      grupos!.value = <Grupo>[];
      json['grupos'].forEach((v) {
        grupos!.add(Grupo.fromJson(v));
      });
    }
  }

  Map<String, dynamic> toJson(User u) {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['id'] = u.id;
    data['username'] = u.name!.value;
    if (grupos != null) {
      data['grupos'] = grupos!.map((v) => v.toJson()).toList();
    }
    return data;
  }
}

class Grupo {
  int? id;
  RxString? nombre = "".obs;
  RxBool? activo = true.obs;
  RxList<Contador>? counters = <Contador>[].obs;

  Grupo({this.id, this.nombre, this.activo, this.counters});

  Grupo.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    nombre!.value = json['nombre'];
    activo!.value = json['activo'];
    if (json['counters'] != null) {
      counters!.value = <Contador>[];
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
  RxString? name = "".obs;
  RxString? descrition = "".obs;
  RxString? image = "".obs;
  RxInt? count = 0.obs;
  RxBool? active = true.obs;

  Contador(
      {this.id,
      this.name,
      this.descrition,
      this.image,
      this.count,
      this.active});

  Contador.fromJson(Map<String, dynamic> json) {
    id = json['id'];
    name!.value = json['name'];
    descrition!.value = json['descrition'];
    image!.value = json['image'];
    count!.value = json['count'];
    active!.value = json['active'];
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
