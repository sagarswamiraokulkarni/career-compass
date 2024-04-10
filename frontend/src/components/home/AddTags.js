import React, { useState } from 'react';
import './AddTags.css';
import {ToastContainer, toast} from 'react-toastify';
import 'react-toastify/dist/ReactToastify.css';
import {orderApi} from "../misc/OrderApi";
import {urlPaths} from "../../Constants";
import ConfirmationModal from './ConfirmationModal';
import {AiOutlineEdit} from "react-icons/ai";
import EditTagModal from "./EditTagModal";

const AddTags = () => {
    const existingTags = JSON.parse(localStorage.getItem('allTags'))
    const allTags=existingTags.map(tag => tag.name);
    const [tags, setTags] = useState(allTags);
    const [newTag, setNewTag] = useState('');
    const [addedTags, setAddedTags] = useState([]);
    const [deletedTags, setDeletedTags] = useState([]);
    const notify = (message) => toast(message);
    const [showConfirmationModal, setShowConfirmationModal] = useState(false);
    const [editingTag, setEditingTag] = useState(null);
    const [editedTagName, setEditedTagName] = useState('');
    const storedUser = JSON.parse(localStorage.getItem('userDetails'));
    const userJson = JSON.parse(localStorage.getItem('user'));

    const handleAddTag = () => {
        if (newTag.trim() !== '' && !tags.includes(newTag.trim()) && !addedTags.includes(newTag.trim())) {
            // setTags([...tags, newTag.trim()]);
            setAddedTags([...addedTags, newTag.trim()]);
            setNewTag('');
        }else if(addedTags.includes(newTag.trim())){
            notify(`Tag: ${newTag.trim()} already in to be added list !..`)
        }else{
            notify(`Tag: ${newTag.trim()} was already added !..`)
        }
    };

    const handleRemoveTag = (tagToRemove,type) => {
        if(type==='existing') {
            setTags(tags.filter(tag => tag !== tagToRemove));
            setDeletedTags([...deletedTags,tagToRemove]);
        }else if(type==='new') {
            setAddedTags(addedTags.filter(tag => tag !== tagToRemove));
        }else if(type==='delete'){
            setTags([...tags,tagToRemove]);
            setDeletedTags(deletedTags.filter(tag => tag !== tagToRemove));
        }
    };

    // const handleConfirm =  () => {
    //     const storedUser = JSON.parse(localStorage.getItem('userDetails'));
    //     const userJson = JSON.parse(localStorage.getItem('user'));
    //     const allTags = JSON.parse(localStorage.getItem('allTags'));
    //     addedTags.map(async (tag) => {
    //         try {
    //             const response = await orderApi.postApiCall(userJson, urlPaths.CREATE_TAG, {name: tag, userId:storedUser.userId});
    //             console.log('API Response:', response);
    //         } catch (error) {
    //             console.error('Error fetching data:', error);
    //         }
    //     })
    //     console.log("begore")
    //     allTags.push(...addedTags);
    //     // allTags.push(addedTags);
    //     localStorage.setItem('allTags', JSON.stringify(allTags));
    //     console.log("after")
    //     console.log('Selected tags:', addedTags);
    // };

    const handleConfirm = () => {
        addedTags.length>0&&setShowConfirmationModal(true);
    };
    const confirmDelete = async () => {
        const storedUser = JSON.parse(localStorage.getItem('userDetails'));
        const userJson = JSON.parse(localStorage.getItem('user'));
        const allTags = JSON.parse(localStorage.getItem('allTags'));
        try {
            await Promise.all(addedTags.map(async (tag) => {
                const response = await orderApi.postApiCall(userJson, urlPaths.CREATE_TAG, {name: tag, userId:storedUser.userId});
                console.log('API Response:', response);
            }));
            const updatedTags = await orderApi.getApiCall(userJson, urlPaths.GET_ALL_TAGS + storedUser.userId);
            const allTagsUpdated=updatedTags.data.map(tag => tag.name);
            setTags(allTagsUpdated);
            localStorage.setItem('allTags', JSON.stringify(updatedTags.data));
            console.log('Selected tags:', addedTags);
            setAddedTags([]);
            setShowConfirmationModal(false);
            notify(`Tags has been added`);
        } catch (error) {
            console.error('Error fetching data:', error);
        }
    };

    const handleConfirmEdit = async (tag, editedTagName) => {
        try {
            const tagId = existingTags.find(tagObj => tagObj.name === tag)?.id;
            const response = await orderApi.putApiCall(userJson, urlPaths.UPDATE_TAG, {
                id: tagId,
                name: editedTagName,
                userId: storedUser.userId,
            });
            console.log('API Response:', response);
            const [getAllTags, unarchivedJobs, archivedJobs] = await Promise.all([
                orderApi.getApiCall(userJson, urlPaths.GET_ALL_TAGS + storedUser.userId),
                orderApi.getApiCall(userJson, urlPaths.GET_UNARCHIVED_JOB_APPLICATIONS + storedUser.userId),
                orderApi.getApiCall(userJson, urlPaths.GET_ARCHIVED_JOB_APPLICATIONS + storedUser.userId)
            ]);
            // const tagNames = getAllTags.data.map(tag => tag.name);
            localStorage.setItem('allTags', JSON.stringify(getAllTags.data));
            localStorage.setItem('unArchivedJobs', JSON.stringify(unarchivedJobs.data));
            localStorage.setItem('archivedJobs', JSON.stringify(archivedJobs.data));
            const existingTagsUpdated = JSON.parse(localStorage.getItem('allTags'))
            const allTagsUpdated=existingTagsUpdated.map(tag => tag.name);
            // const updatedTags = existingTags.map((t) => (t.name === tag ? { ...t, name: editedTagName } : t));
            // // setTags(updatedTags);
            // localStorage.setItem('allTags', JSON.stringify(updatedTags));
            // const updatedTagsArray=updatedTags.map(tag => tag.name);
            setTags(allTagsUpdated);
            setEditingTag(null);
            setEditedTagName('');
            notify(`Tag: ${tag} has been changed to ${editedTagName}.`)

        } catch (error) {
            console.error('Error updating tag:', error);
        }
    };
    const handleEdit = (tag) => {
        setEditingTag(tag);
        setEditedTagName(tag);
      };
    return (
        <div className="add-tags-background-container">
        <div className="add-tags-container">
            <ToastContainer/>
            {tags.length>0&&<h2 className="add-tags-title">Existing Tags</h2>}
            <div className="tag-list">
                {tags.map((tag, index) => (
                    <span key={index} className={`added-tag tag-${index}`}>
                        {tag}
                            <AiOutlineEdit onClick={() => handleEdit(tag)}/>
                    </span>
                ))}
            </div>
            <div className="input-container">
                <input
                    type="text"
                    value={newTag}
                    onChange={(e) => setNewTag(e.target.value)}
                    placeholder="Add a new tag"
                    className="tag-input"
                />
                <button className="add-tag-btn" onClick={handleAddTag}>Add Tag</button>
            </div>
            {addedTags.length > 0 && (
                <div className="added-tags-container">
                    <h4>Tags to be Added:</h4>
                    <div className="added-tag-list">
                        {addedTags.map((tag, index) => (
                            <span key={index} className={`added-tag tag-${index}`}>
                    {tag}
                                <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag, "new")}>x</button>
                </span>
                        ))}
                    </div>
                </div>
            )}
            {deletedTags.length>0&&<div className="added-tags-container">
                <h4>Tags to be Deleted:</h4>
                <div className="added-tag-list">
                    {deletedTags.map((tag, index) => (
                        <span key={index} className={`added-tag tag-${index}`}>
                            {tag}
                            <button className="remove-tag-btn" onClick={() => handleRemoveTag(tag,"delete")}>x</button>
                        </span>
                    ))}
                </div>
            </div>}
            <button className="confirm-btn" onClick={handleConfirm}>Confirm</button>
            <ConfirmationModal
                show={showConfirmationModal}
                onHide={() => setShowConfirmationModal(false)}
                onConfirm={confirmDelete}
                bodyContent={
                    <>
                        Are you sure you want to add this tags?:&nbsp;
                        {addedTags.map((tag, index) => (
                            <span key={index}>
                            {index > 0 && ", "}
                            {tag}
                            </span>
                        ))}
                    </>
                }
            />
            <EditTagModal
                show={editingTag !== null}
                onHide={() => setEditingTag(null)}
                onConfirm={handleConfirmEdit}
                tag={editingTag}
                editedTagName={editedTagName}
                setEditedTagName={setEditedTagName}
            />
        </div>
        </div>
    );
};

export default AddTags;