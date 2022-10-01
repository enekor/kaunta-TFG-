class Register {
  String? user;
  String? password;

  Register({this.user, this.password});

  Register.fromJson(Map<String, dynamic> json) {
    user = json['user'];
    password = json['password'];
  }

  Map<String, dynamic> toJson() {
    final Map<String, dynamic> data = <String, dynamic>{};
    data['user'] = user;
    data['password'] = password;
    return data;
  }
}
