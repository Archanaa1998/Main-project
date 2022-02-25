from flask import Flask, render_template, request, jsonify
import time
import datetime


from DBConnection import Db


app = Flask(__name__)
staticpath="C:\\Users\\archa\\PycharmProjects\\farmersapp\\static\\"



@app.route('/')
def landing():
    return render_template('landing.html')
@app.route('/login')
def login():
    return render_template('login.html')


@app.route('/login_post',methods=['post'])
def login_post():
    username = request.form['textfield']

    password = request.form['textfield2']
    qry="select loginid,type from login where username='"+username+"' and password='"+password+"'"
    a=Db()
    res=a.selectOne(qry)
    if res is not None:

        if res["type"]=="admin":

            return dashboard()
        else:
            return "No"
    else:
        return '''<script>alert('Wrong password'); window.location='/login'</script>'''



@app.route('/addcontactbook')
def addcontactbook():
    return render_template('addcontactbook.html')


@app.route('/addcontactbook_post',methods=['post'])
def addcontactbook_post():
    dept=request.form['textfield']
    name=request.form['textfield2']
    emailid=request.form['textfield3']
    phoneno=request.form['textfield4']
    a = Db()
    qry="INSERT INTO contactbook(`name`, `department`, `phoneno`, `emailid`) VALUES('"+dept+"', '"+name+"', '"+phoneno+"','"+emailid+"')"
    res = a.insert(qry)
    return '''<script>alert('-----------'); window.location='/addcontactbook'</script>'''


@app.route('/addcrop')
def addcrop():
    return render_template('addcrop.html')

@app.route('/addcrop_post',methods=['post'])
def addcrop_post():
    category = request.form['textfield']
    name = request.form['textfield2']
    description = request.form['textfield3']
    photo = request.files['textfield4']

    dt=time.strftime("%Y%m%d-%H%M%S")
    photo.save(staticpath+"crop_img\\"+dt+".jpg")
    path = "/static/crop_img/"+dt+".jpg"
    a = Db()
    qry = "INSERT INTO crops(`category`,`name`,`description`,`photo`) VALUES('"+category+"', '"+name+"', '"+description+"','"+path+"')"

    res=a.insert(qry)
    return '''<script>alert('Crop added'); window.location='/viewcrop'</script>'''



@app.route('/addfertilizer')
def addfertilizer():
    return render_template('addfertilizer.html')




@app.route('/addfertilizer_post',methods=['post'])
def addfertilizer_post():
    type = request.form['textfield']
    name = request.form['textfield2']
    description = request.form['textfield3']
    photo = request.files['textfield4']

    dt = time.strftime("%Y%m%d-%H%M%S")
    photo.save(staticpath + "fp_img\\" + dt + ".jpg")
    path = "/static/fp_img/" + dt + ".jpg"

    a = Db()
    qry ="INSERT INTO `fp`(`type`,`name`,`description`,`photo`) VALUES('"+type+"','"+name+"','"+description+"','"+path+"')"
    res = a.insert(qry)
    return 'ok'



@app.route('/addmachinery')
def addmachinery():
    return render_template('addmachinery.html')


@app.route('/addmachinery_post',methods=['post'])
def addmachinery_post():
    name = request.form['textfield']
    modelno = request.form['textfield2']
    rentamount = request.form['textfield3']

    photo = request.files['file']
    description = request.form['textarea']
    purpose = request.form['textfield4']

    dt = time.strftime("%Y%m%d-%H%M%S")
    photo.save(staticpath + "machinery_img\\" + dt + ".jpg")
    path = "/static/machinery_img/" + dt + ".jpg"

    a = Db()
    qry="INSERT INTO machinery(`name`,`modelno`,`photo`,`rentamount`,`description`,`purpose`) VALUES('"+name+"','"+modelno+"','"+path+"','"+rentamount+"','"+description+"','"+purpose+"')"
    res = a.insert(qry)
    return 'ok'



@app.route('/addnotification')
def addnotification():
    return render_template('addnotification.html')


@app.route('/addnotification_post',methods=['post'])
def addnotification_post():
    subject = request.form['textfield']

    notification = request.form['textarea']
    a = Db()
    qry="INSERT INTO notification(`subject`, `notification`, date, time) VALUES('"+subject+"', '"+notification+"',curdate(),curtime())"
    res = a.insert(qry)
    return 'ok'



@app.route('/editcontactbook')
def editcontactbook():
    return render_template('editcontactbook.html')


@app.route('/editcontactbook_post',methods=['post'])
def editcontactbook_post():
    department = request.form['textfield']

    name = request.form['textfield2']
    photo = request.form['file']
    emailid = request.form['textfield3']

    id = request.form['id']
    a = Db()
    qry = "update contactbook set department='" + department + "', name='" + name + "',  photo='" + photo + "',emailid='" + emailid + "', where fpid='" + str(
        id) + "'"
    res = a.update(qry)
    return 'ok'



@app.route('/editfertilizer')
def editfertilizer():
    return render_template('editfertilizer.html')


@app.route('/editfertilizer_post',methods=['post'])
def editfertilizer_post():
    type = request.form['textfield']
    name = request.form['textfield2']
    description = request.form['textfield3']
    photo = request.form['file']

    id = request.form['id']
    a = Db()
    qry = "update fp set type='" + type + "',name='" + name + "', description='" + description + "', photo='" + photo + "' where fpid='" + str(
        id) + "'"
    res = a.update(qry)
    return 'ok'


@app.route('/editmachinery')
def editmachinery():
    return render_template('editmachinery.html')



@app.route('/editmachinery_post',methods=['post'])
def editmachinery_post():
    name = request.form['textfield']
    modelno = request.form['textfield2']
    rentamount = request.form['textfield3']

    photo = request.form['file']
    description = request.form['textarea']
    purpose = request.form['textfield4']
    id = request.form['id']
    a = Db()
    qry = "update machinery set name='" + name + "',modelno='" + modelno + "', rentamount='"+rentamount+"', photo='"+photo+"', description='"+description+"', purpose='"+purpose+"' where mid='" + str(
        id) + "'"
    res = a.update(qry)
    return 'ok'


@app.route('/editnotification')
def editnotification():
    return render_template('editnotification.html')


@app.route('/editnotification_post',methods=['post'])
def editnotification_post():
    subject = request.form['textfield']
    notification = request.form['textarea']
    id = request.form['id']
    a=Db()
    qry="update notification set subject='"+subject+"',notification='"+notification+"' where nid='"+str(id)+"'"
    res=a.update(qry)
    return 'ok'


@app.route('/viewcomplaint_post',methods=['post'])
def viewcomplaint_post():
    from_= request.form['textfield']
    to = request.form['textfield2']
    return 'ok'
@app.route('/viewcomplaint')
def viewcomplaint():
    a = Db()
    qry = "SELECT * FROM `complaint`"
    res = a.select(qry)
    return render_template('viewcomplaint.html')




@app.route('/viewfeedback_post',methods=['post'])
def viewfeedback_post():
    from_ = request.form['textfield']
    to = request.form['textfield2']

    return 'ok'


@app.route('/viewfeedback')
def viewfeedback():
    a = Db()
    qry = "SELECT * FROM `feedback`"
    res = a.select(qry)
    return render_template('viewfeedback.html',data=res)






@app.route('/viewcontactbook')
def viewcontactbook():
    a = Db()
    qry = "SELECT * FROM `contactbook`"
    res = a.select(qry)
    return render_template('viewcontactbook.html',data=res)



@app.route('/viewcontactbook_post',methods=['post'])
def viewcontactbook_post():
    department_ = request.form['submit']
    return 'ok'


@app.route('/viewcrop')
def viewcrop():
    a = Db()
    qry = "SELECT * FROM `crops`"
    res = a.select(qry)
    qry1="SELECT DISTINCT `category` FROM `crops`"
    res1=a.select(qry1)
    return render_template('viewcrop.html',data=res, cat=res1)


@app.route('/deletecrop/<cid>')
def deletecrop(cid):
    a=Db()
    qry = "delete from crops where cropid='" +str(cid)+ "'"
    res=a.delete(qry)
    return '''<script>alert('Crop Deleted '); window.location='/viewcrop'</script>'''

@app.route('/viewcropfilter_post',methods=['post'])
def viewcropfilter_post():

    btn=request.form['Submit']
    a = Db()
    if btn=="Filter":
        category_ = request.form['select']

        qry="select * from crops where category='"+category_+"'"
        res=a.select(qry)

        qry1 = "SELECT DISTINCT `category` FROM `crops`"
        res1 = a.select(qry1)
        return render_template('viewcrop.html', data=res, cat=res1)
    elif btn=="Go":
        name_ = request.form['textfield3']

        qry = "select * from crops where name='" + name_ + "'"
        res = a.select(qry)

        qry1 = "SELECT DISTINCT `category` FROM `crops`"
        res1 = a.select(qry1)
        return render_template('viewcrop.html', data=res, cat=res1)


@app.route('/viewfertilizer')
def viewfertilizer():
    a = Db()
    qry = "SELECT * FROM `fp`"
    res = a.select(qry)
    return render_template('viewferilizer.html',data=res)

@app.route('/viewfertilizer_post',methods=['post'])
def viewfertilizer_post():
    category_ = request.form['submit']
    name_ = request.form['textfield3']
    return 'ok'


@app.route('/viewmachinery')
def viewmachinery():
    a = Db()
    qry = "SELECT * FROM `machinery`"
    res = a.select(qry)
    return render_template('viewmachinery.html',data=res)

@app.route('/viewmachinery_post',methods=['post'])
def viewmachinery_post():
    purpose_ = request.form['textfield3']
    return 'ok'


@app.route('/viewnotification')
def viewnotification():
    a = Db()
    qry = "SELECT * FROM `notification`"
    res = a.select(qry)
    return render_template('viewnotification.html',data=res)

@app.route('/viewnotification_post',methods=['post'])
def viewnotification_post():
    from_ = request.form['textfield']
    to = request.form['textfield2']
    subject = request.form['textfield3']
    return 'ok'

@app.route('/dashboard')
def dashboard():
    return render_template('/temp2.html')




@app.route('/editcrop/<cid>')
def editcrop(cid):
    a=Db()
    qry="select * from crops where cropid='"+cid+"'"
    res=a.selectOne(qry)

    return render_template('/editcrop.html',data=res)


@app.route('/editcrop_post',methods=['post'])
def editcrop_post():
    cid = request.form['cid']
    category = request.form['textfield']
    name = request.form['textfield2']
    description = request.form['textfield3']
    a = Db()
    if 'textfield4' in request.files:

        photo = request.files['textfield4']
        if photo.filename!='':

            dt=time.strftime("%Y%m%d-%H%M%S")
            photo.save(staticpath+"crop_img\\"+dt+".jpg")
            path = "/static/crop_img/"+dt+".jpg"

            qry = "UPDATE crops set category='"+category+"', name='"+name+"',description=de'"+description+"',photo='"+path+"' where cropid='"+cid+"'"

            res=a.insert(qry)
        else:
            qry = "UPDATE crops set category='" + category + "', name='" + name + "',description='" + description + "' where cropid='" + cid + "'"

            res = a.insert(qry)
    else:
        qry = "UPDATE crops set category='" + category + "', name='" + name + "',description='" + description + "' where cropid='" + cid + "'"

        res = a.insert(qry)

    return '''<script>alert('Save changes'); window.location='/viewcrop'</script>'''



#___________________________Android------------------












@app.route('/and_login_post',methods=['post'])
def and_login_post():
    username = request.form['email']

    password = request.form['password']

    qry = "select loginid,type from login where username='" + username + "' and password='" + password + "'"
    a = Db()
    res = a.selectOne(qry)
    if res is not None:
        return jsonify(status="ok",lid=res["loginid"])
    else:
        return jsonify(status="No")






@app.route('/farmerregistration_post',methods=['post'])
def farmerregistration_post():
    name = request.form['name']
    place = request.form['place']
    pincode = request.form['pincode']
    post = request.form['post']
    ty=request.form["ty"]
    district = request.form['district']
    emailid = request.form['emailid']
    housename=request.form['housename']
    phoneno = request.form['phoneno']
    password = request.form['password']
    photo=request.form["photo"]
    import time, datetime
    from encodings.base64_codec import base64_decode
    import base64

    timestr = time.strftime("%Y%m%d-%H%M%S")
    print(timestr)
    a = base64.b64decode(photo)
    fh = open("static/user/" + timestr + ".jpg", "wb")
    path = "/static/user/" + timestr + ".jpg"
    fh.write(a)
    fh.close()
    a = Db()
    qry="INSERT INTO `login` (`username`,`password`,`type`) VALUES ('"+emailid+"','"+password+"','farmer')"

    res=a.insert(qry)
    qry1 = "INSERT INTO`user`(loginid,`name`,`place`,`post`,`house`,`district`,`email`,pincode,`phoneno`,photo,user_type) VALUES ('"+str(res)+"','" + name + "','" + place + "','" + post + "','" + housename + "','" + district + "','" + emailid + "','" + pincode + "','" + phoneno + "','" + path + "','" + ty + "')"

    res1 = a.insert(qry1)

    if res is not None:
        return jsonify(status="ok")
    else:
        return jsonify(status="No")


@app.route('/userviewcrop_post', methods=['post'])
def userviewcrop_post():
    a = Db()
    qry = "SELECT * FROM `crops`"
    res = a.select(qry)

    return jsonify(status="ok",users=res)




if __name__ == '__main__':
    app.run(debug=True,host='0.0.0.0')
